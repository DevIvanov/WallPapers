package com.example.ivanov_p3.ui.fragment

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class GoogleSearchAsyncTask : AsyncTask<URL?, Int?, String?>() {

    var responseCode: Int = 0
    var responseMessage: String = ""
    var result: String = ""

    override fun onPreExecute() {
        Log.d(TAG, "AsyncTask - onPreExecute")
        // show mProgressBar
        binding.progressBar.visibility = View.VISIBLE
    }

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

                while ((rd.readLine().also { line = it }) != null){
                    sb.append(line+"\n")
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

        // hide mProgressBar
        binding.progressBar.visibility = View.GONE

        // make TextView scrollable
        binding.textView.movementMethod = ScrollingMovementMethod()
        // show result
        binding.textView.text = result
    }
}