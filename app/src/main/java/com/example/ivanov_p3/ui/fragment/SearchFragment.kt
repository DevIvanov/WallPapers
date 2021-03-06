package com.example.ivanov_p3.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.ImagesEntity
import com.example.data.network.data.UnsplashPagingSource
import com.example.data.network.data.UnsplashPhoto
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentSearchBinding
import com.example.ivanov_p3.ui.adapter.UnsplashPhotoAdapter
import com.example.ivanov_p3.ui.viewmodel.GalleryViewModel
import com.example.ivanov_p3.ui.viewmodel.HistoryViewModel
import com.example.ivanov_p3.util.view.PreferenceHelper
import com.example.ivanov_p3.util.view.PreferenceHelper.columns
import com.example.ivanov_p3.util.view.PreferenceHelper.query
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment: BaseFragment(R.layout.fragment_search),
    UnsplashPhotoAdapter.OnItemClickListener  {

    private lateinit var binding: FragmentSearchBinding
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    private lateinit var gridView: RecyclerView
    private lateinit var adapter: UnsplashPhotoAdapter

    private val args by navArgs<SearchFragmentArgs>()
    private var query = ""
    private var numColumns: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        prefs = PreferenceHelper.customPreference(requireContext(), PreferenceHelper.CUSTOM_PREF_NAME)
        adapter = UnsplashPhotoAdapter(this)

        setText()
        backPressed()
        onClick()

        gridView = binding.gridView

        if (prefs.columns)
            setTwoColumns()
        else
            setThreeColumns()

        val query = prefs.query.toString()
        galleryViewModel.searchPhotos(query)

        setAdapter()

        return binding.root
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
            searchImages()
        }
        binding.columnsImage.setOnClickListener {
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
        query = binding.editText.text.toString()
        hideKeyboard()
        if (query == "")
            toast(R.string.enter_the_text)
        else if (!checkInternetConnection()){
            showAlertDialog("",getString(R.string.check_interet))
        }else{
            prefs.query = query

            galleryViewModel.searchPhotos(query)
            addHistoryInDatabase()
        }
    }

    private fun addHistoryInDatabase(){
        val countImages = UnsplashPagingSource.total!!.toInt()

        val history = History(0, query, countImages, getCurrentTime(), false)
        historyViewModel.addData(history)
    }

    private fun setAdapter() {
        gridView.adapter = adapter
        galleryViewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                gridView.isVisible = loadState.source.refresh is LoadState.NotLoading

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    gridView.isVisible = false
                    textViewEmpty!!.isVisible = true
                } else {
                    textViewEmpty!!.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val imageEntity = ImagesEntity(0, photo.urls.full, photo.urls.regular, photo.created_at,
            photo.width, photo.height, photo.color, photo.user.name, photo.description)
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(imageEntity)
        findNavController().navigate(action)
    }
}