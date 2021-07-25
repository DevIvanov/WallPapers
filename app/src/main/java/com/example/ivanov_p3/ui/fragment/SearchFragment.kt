package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.data.UnsplashPhoto
import com.example.ivanov_p3.databinding.FragmentSearchBinding
import com.example.ivanov_p3.ui.GalleryViewModel
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.ui.UnsplashPhotoAdapter
import com.example.ivanov_p3.ui.adapter.SearchGridViewAdapter
import com.example.ivanov_p3.util.GoogleSearchAsyncTask
import com.example.ivanov_p3.util.view.PreferenceHelper
import com.example.ivanov_p3.util.view.PreferenceHelper.columns
import com.example.ivanov_p3.util.view.PreferenceHelper.query
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("StaticFieldLeak")
lateinit var binding: FragmentSearchBinding

@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment: BaseFragment(R.layout.fragment_search),
    UnsplashPhotoAdapter.OnItemClickListener  {

    private lateinit var mGalleryViewModel: GalleryViewModel
    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var prefs: SharedPreferences
    private lateinit var gridView: RecyclerView
    private var numColumns: Int = 1
    private lateinit var adapter: UnsplashPhotoAdapter

    private val args by navArgs<SearchFragmentArgs>()
    private var searchString: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        mGalleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        prefs = PreferenceHelper.customPreference(requireContext(), PreferenceHelper.CUSTOM_PREF_NAME)

        setText()
        backPressed()
        onClick()

        if (prefs.columns) {
            numColumns = 2
        }else {
            numColumns = 3
        }

        gridView = binding.gridView
        gridView.layoutManager = GridLayoutManager(requireContext(), numColumns)

//        val query = prefs.query.toString()
        adapter =
            UnsplashPhotoAdapter(this)
        setAdapter()

        return binding.root
    }

    private fun setWidthHeight(): Int {
        return if (prefs.columns) {
            540
        } else {
            360
        }
    }

    private fun setText(){
        if(args.currentQuery != null)
            binding.editText.setText(args.currentQuery)
    }

    private fun onClick() {
        binding.editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                searchImages()
            }
            false
        })
        binding.searchImage.setOnClickListener {
            hideKeyboard()
            searchImages()
        }
        binding.columnsImage.setOnClickListener {
            val query = prefs.query.toString()
            if (prefs.columns) {
                setThreeColumns()
                prefs.columns = false
            }else{
                setTwoColumns()
                prefs.columns = true
            }

            setAdapter()
        }
    }

    private fun setTwoColumns() {
        binding.columnsImage.setImageResource(R.drawable.two_columns)
        numColumns = 2
        gridView.layoutManager = GridLayoutManager(requireContext(), numColumns)
    }

    private fun setThreeColumns() {
        binding.columnsImage.setImageResource(R.drawable.three_columns)
        numColumns = 3
        gridView.layoutManager = GridLayoutManager(requireContext(), numColumns)
    }

    private fun searchImages() {

        searchString = binding.editText.text.toString()
        if (searchString == "")
            toast(R.string.enter_the_text)
        else if (!checkInternetConnection()){
            showAlertDialog("",getString(R.string.check_interet))
        }else{
            prefs.query = searchString

            mGalleryViewModel.searchPhotos(searchString!!)
        }
    }


    private fun setAdapter() {
        gridView.adapter = adapter

        mGalleryViewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        TODO("Not yet implemented")
    }

}