package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Insets.add
import android.os.AsyncTask
import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList



class GoogleSearchAsyncTask(@SuppressLint("StaticFieldLeak") private val context: Context) : AsyncTask<URL?, Int?, String?>() { //(context: Context)

    var responseCode: Int = 0
    var responseMessage: String = ""
    var result: String = ""
    var src: String = ""
    var bitmap: Bitmap? = null

    companion object {
        var bitmapList: List<Bitmap?> = listOf()
    }

    override fun onPreExecute() {
        Log.d(TAG, "AsyncTask - onPreExecute")
        // show mProgressBar
        bitmapList = listOf()
        binding.progressBar.visibility = View.VISIBLE
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
            responseCode = conn?.getResponseCode()!!
            responseMessage = conn.getResponseMessage()
        } catch (e: IOException) {
            Log.e(TAG, "Http getting response code ERROR " + e.toString())
        }
        Log.d(TAG, "Http response code =" + responseCode.toString() + " message=" + responseMessage)
        try {
            return if (responseCode == 200) {

                // response OK
                val rd = BufferedReader(InputStreamReader(conn?.getInputStream()))
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
                            bitmap = getBitmapFromURL(src)
                            Log.d("LOG", "${bitmap}")
//                            var string = encodePhoto(bitmap)
//                            Log.d("LOG", "${string}")
                            bitmapList = bitmapList.plus(bitmap)
                            Log.d("LOG", "${bitmapList}")
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

        binding.progressBar.visibility = View.GONE

        val searchFragment = SearchFragment()
        searchFragment.setAdapter(context)

    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url
                .openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodePhoto(photo: Bitmap?): String? {
        val bos = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val byteArray: ByteArray = bos.toByteArray()
        return Base64.getEncoder().encodeToString(byteArray)
    }


}