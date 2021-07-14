package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentSearchBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.ui.adapter.SearchGridViewAdapter
import com.example.ivanov_p3.util.view.GoogleSearchAsyncTask
import com.example.ivanov_p3.util.view.PreferenceHelper
import com.example.ivanov_p3.util.view.PreferenceHelper.query
import kotlinx.coroutines.DelicateCoroutinesApi
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
    lateinit var binding: FragmentSearchBinding

@DelicateCoroutinesApi
class SearchFragment: BaseFragment(R.layout.fragment_search) {

    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var mHistoryViewModel: HistoryViewModel
    private val args by navArgs<SearchFragmentArgs>()
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        prefs = PreferenceHelper.customPreference(requireContext(), PreferenceHelper.CUSTOM_PREF_NAME)

//        val intent = Intent()
//        if (Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                SearchRecentSuggestions(requireContext(), MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
//                    .saveRecentQuery(query, null)
//            }
//        }

        setText()
        backPressed()
        onClick()

        val query = prefs.query.toString()
        setAdapter(requireContext(), query)

        return binding.root
    }

    private fun setText(){
        if(args.currentQuery != null)
            binding.editText.setText(args.currentQuery)
    }

    private fun onClick() {
        binding.button.setOnClickListener {
            hideKeyboard()
            val searchString = binding.editText.text.toString()

            val history = History(0, searchString, 13, getCurrentTime(), false)
            mHistoryViewModel.addData(history)
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
                Log.e(TAG, "ERROR converting String to URL $e")
            }
            Log.d(TAG, "Url = $urlString")

            // start AsyncTask
            prefs.query = searchString

            val searchTask = GoogleSearchAsyncTask(requireContext(), searchString)
            searchTask.execute(url)
        }
    }

    private fun getCurrentTime(): String {
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        val simpleDateFormat = SimpleDateFormat("dd.MM HH:mm")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        val month = monthName[SimpleDateFormat("MM").format(Date()).toInt() - 1]
        val time: String= currentDateAndTime.replaceRange(2, 5, " $month")

        return time
    }

    fun setAdapter(context: Context, query: String) {
        val adapter =
            SearchGridViewAdapter(context, query)
        val gridView = binding.gridView
        gridView.adapter = adapter
    }
}