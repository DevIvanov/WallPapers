package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentSearchBinding
import com.example.ivanov_p3.ui.adapter.GridViewAdapter
import java.net.MalformedURLException
import java.net.URL

@SuppressLint("StaticFieldLeak")
    lateinit var binding: FragmentSearchBinding

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        backPressed()
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.button.setOnClickListener {
            val searchString = binding.editText.text.toString()
            toast(searchString)

            val searchStringNoSpaces = searchString.replace(" ", "+")
            val key = "AIzaSyCmp7XwRBMvUmPXxUtNzEr22BvaZb4sQJw"
            val searchId = "faaf876d27c79cad7" // 6c72a82ea0ce194af

            val urlString =
                "https://www.googleapis.com/customsearch/v1?q=$searchStringNoSpaces&key=$key&cx=$searchId&alt=json"
            var url: URL? = null
            try {
                url = URL(urlString)
            } catch (e: MalformedURLException) {
                Log.e(TAG, "ERROR converting String to URL " + e.toString())
            }
            Log.d(TAG, "Url = $urlString")

            // start AsyncTask
            val searchTask = GoogleSearchAsyncTask()
            searchTask.execute(url)

            setAdapter()   //searchTask.getArray()
        }

    }

    private fun setAdapter() { //array: ArrayList<Bitmap?> ?= null
        val adapter = GridViewAdapter(requireContext()) //, array
        val gridView = binding.gridView
        gridView.adapter = adapter
    }
}