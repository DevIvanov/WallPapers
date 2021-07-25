package com.example.ivanov_p3.util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.data.database.ImagesEntity
import com.example.domain.model.History
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.fragment.SearchFragment
import com.example.ivanov_p3.ui.fragment.binding
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@DelicateCoroutinesApi
class GoogleSearchAsyncTask(
    @SuppressLint("StaticFieldLeak") private val mContext: Context,
    private val query: String, private val widthHeight: Int,
    private val currentTime: String, private val mHistoryViewModel: HistoryViewModel
) : AsyncTask<URL?, Int?, String?>() {

    var responseCode: Int = 0
    var responseMessage: String = ""
    var result: String = ""
    var src: String = ""
    var src2: String = ""
    var width: String = ""
    var height: String = ""
    var displayLink: String = ""
    var countImages: Int = 0

    companion object {
        var imagesEntityList: List<ImagesEntity?> = listOf()
    }

    override fun onPreExecute() {
        Log.d(TAG, "AsyncTask - onPreExecute")

        binding.progressBar.visibility = View.VISIBLE
        imagesEntityList = listOf()
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
            Log.e(TAG, "Http connection ERROR $e")
        }
        try {
            responseCode = conn?.responseCode!!
            responseMessage = conn.responseMessage
        } catch (e: IOException) {
            Log.e(TAG, "Http getting response code ERROR $e")
        }
        Log.d(TAG, "Http response code =$responseCode message=$responseMessage")
        try {
            return if (responseCode == 200) {

                // response OK
                readJson(conn)

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
            Log.e(TAG, "Http Response ERROR $e")
        }
        return null
    }

    private fun readJson (conn: HttpURLConnection?): String {
        val rd = BufferedReader(InputStreamReader(conn?.inputStream))
        val sb = StringBuilder()
        var line: String ?= null

        while ((rd.readLine().also { line = it }) != null){
            if (line!!.contains("\"displayLink\"")){
                displayLink = line.toString()
                displayLink = displayLink.replace("\"displayLink\":", "")
                    .replace("\"", "").replace(" ", "")
                    .replace(",", "")
            }
            if (line!!.contains("\"src\":")) {
                if(line!!.contains(",")) {
                    if (!line!!.contains("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT7fQCk_5ECyPMApCb-Ck41JIWZbx4P4QvTddt54CVDG69RAwjPKriibDw")) {
                        src = line.toString()
                        src = src.replace("\"src\":", "")
                            .replace("\"", "").replace(" ", "")
                            .replace(",", "")
                    }
                }
            }
            if (line!!.contains("\"width\"")){
                width = line.toString()
                width = width.replace("\"width\":", "")
                    .replace("\"", "").replace(" ", "")
                    .replace(",", "")
            }
            if (line!!.contains("\"height\"")) {
                height = line.toString()
                height = height.replace("\"height\":", "")
                    .replace("\"", "").replace(" ", "")
                    .replace(",", "")
            }
            if (line!!.contains("\"src\": \"https://images") && !line!!.contains(",")) {
                src2 = line.toString()
                src2 = src2.replace("\"src\":", "")
                    .replace("\"", "").replace(" ", "")

                sb.append(src2 + "\n")
                countImages++

                if (displayLink != "" && src != "" && width != "" && height != "" && src2 != ""){
                    imagesEntityList = imagesEntityList.plus(
                        ImagesEntity(
                            0, src2, currentTime, width, height,
                            0, displayLink
                        )
                    )
                }
                displayLink = ""
                src = ""
                width = ""
                height = ""
                src2 = ""
            }
        }

        rd.close()
        conn?.disconnect()
        result = sb.toString()
        Log.d(TAG, "result=$result")
        return result
    }

    override fun onProgressUpdate(vararg values: Int?) {
        Log.d(TAG, "AsyncTask - onProgressUpdate, progress=$values")
    }

    override fun onPostExecute(result: String?) {
        Log.d(TAG, "AsyncTask - onPostExecute, result=$result")

        binding.progressBar.visibility = View.GONE
        val searchFragment = SearchFragment()
//        searchFragment.setAdapter(mContext, query, widthHeight)
        val history = History(0, query, countImages, currentTime, false)
        mHistoryViewModel.addData(history)
    }
}