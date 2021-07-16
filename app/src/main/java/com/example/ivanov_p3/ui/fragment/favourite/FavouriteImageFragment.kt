package com.example.ivanov_p3.ui.fragment.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouriteImageBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.ui.adapter.FavouriteGridViewAdapter


class FavouriteImageFragment : BaseFragment(R.layout.fragment_favourite_image) {

    private lateinit var binding: FragmentFavouriteImageBinding
    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var adapter: FavouriteGridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteImageBinding.inflate(inflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        adapter = FavouriteGridViewAdapter(
            mImagesViewModel = mImagesViewModel,
            mContext = requireContext()
        )

        setAdapter()
        readData()
        return binding.root
    }

    private fun setAdapter() {
        val gridView = binding.gridView
        gridView.layoutManager = GridLayoutManager(requireContext(), 1)
        gridView.adapter = adapter
    }

    private fun readData() {
        mImagesViewModel.readAllData.observe(viewLifecycleOwner, Observer { images ->
            setAdapter()
            adapter.setData(images)
        })
        Log.d("Database", "Adapter set")
    }
}