package com.example.ivanov_p3.ui.fragment.favourite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouriteImageBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.ui.adapter.FavouriteGridViewAdapter

class FavouriteImageFragment : BaseFragment(R.layout.fragment_favourite_image) {

    private lateinit var binding: FragmentFavouriteImageBinding
    private lateinit var mImagesViewModel: ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteImageBinding.inflate(inflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        readData()
        return binding.root
    }

    private fun readData() {
        mImagesViewModel.readAllData.observe(viewLifecycleOwner, Observer { images ->
            setAdapter(requireContext(), images)
        })
    }

    private fun setAdapter(context: Context, images: List<Images>) {
        val adapter = FavouriteGridViewAdapter(context, images)
        val gridView = binding.gridView
        gridView.adapter = adapter
    }

}