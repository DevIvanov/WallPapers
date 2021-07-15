package com.example.ivanov_p3.util.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.data.database.ImagesEntity
import com.example.ivanov_p3.ui.fragment.SearchFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@DelicateCoroutinesApi
class GoogleSearchAsyncTask(@SuppressLint("StaticFieldLeak") private val context: Context,
                            private val query: String, private val widthHeight: Int
) : AsyncTask<URL?, Int?, String?>() {

    var responseCode: Int = 0
    var responseMessage: String = ""
    var result: String = ""
    var src: String = ""

    companion object {
        var imagesEntityList: List<ImagesEntity?> = listOf()

    }

    override fun onPreExecute() {
        Log.d(TAG, "AsyncTask - onPreExecute")
        // clean list
        imagesEntityList = listOf()
        // show mProgressBar
        com.example.ivanov_p3.ui.fragment.binding.progressBar.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doInBackground(vararg p0: URL?): String? {
        val url: URL? = p0[0]
        Log.d(TAG, "AsyncTask - doInBackground, url=$url")

        // Http connection
        var conn: HttpURLConnection? = null
        try {
            conn = url?.openConnection() as HttpURLConnection
        } catch (e: IOException) {
            Log.e(TAG, "Http connection ERROR " + e.toString())
        }
        try {
            responseCode = conn?.responseCode!!
            responseMessage = conn.responseMessage
        } catch (e: IOException) {
            Log.e(TAG, "Http getting response code ERROR " + e.toString())
        }
        Log.d(TAG, "Http response code =" + responseCode.toString() + " message=" + responseMessage)
        try {
            return if (responseCode == 200) {

                // response OK
                val rd = BufferedReader(InputStreamReader(conn?.inputStream))
                val sb = StringBuilder()
                var line: String ?= null
                var i = 0

                while ((rd.readLine().also { line = it }) != null){
                    if (i<10) {
                        if (line!!.contains("\"src\": \"https://images")) {
                            src = line.toString()
                            src = src.replace("\"src\":", "")
                                .replace("\"", "").replace(" ", "")
                                .replace(",", "")

                            // add in list
                            imagesEntityList = imagesEntityList.plus(ImagesEntity(0, "", src))  //stringBitmap

                            i++
                            sb.append(src + "\n")
                        }
                    }
                }

                rd.close()
                conn?.disconnect()
                result = sb.toString()
                Log.d(TAG, "result=$result")
                result

            } else {

                // response problem
                val errorMsg = """
                    Http ERROR response $responseMessage
                    Are you online ? 
                    Make sure to replace in code your own Google API key and Search Engine ID
                    """.trimIndent()
                Log.e(TAG, errorMsg)
                result = errorMsg
                result
            }
        } catch (e: IOException) {
            Log.e(TAG, "Http Response ERROR " + e.toString())
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        Log.d(TAG, "AsyncTask - onProgressUpdate, progress=$values")
    }

    override fun onPostExecute(result: String?) {
        Log.d(TAG, "AsyncTask - onPostExecute, result=$result")

        com.example.ivanov_p3.ui.fragment.binding.progressBar.visibility = View.GONE

        //set adapter
        val searchFragment = SearchFragment()
        searchFragment.setAdapter(context, query, widthHeight)

    }
}