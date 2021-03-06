package com.example.ivanov_p3.ui.fragment.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouriteImageBinding
import com.example.ivanov_p3.ui.adapter.FavouriteGridAdapter
import com.example.ivanov_p3.ui.viewmodel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteImageFragment : BaseFragment(R.layout.fragment_favourite_image),
    FavouriteGridAdapter.OnItemClickListener,
    FavouriteGridAdapter.OnDeleteClickListener{

    private lateinit var binding: FragmentFavouriteImageBinding
    private val imagesViewModel: ImagesViewModel by viewModels()
    private lateinit var adapter: FavouriteGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteImageBinding.inflate(inflater, container, false)

        adapter = FavouriteGridAdapter(
            itemListener = this,
            deleteListener = this
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
        imagesViewModel.readAllData.observe(viewLifecycleOwner, Observer { images ->
            adapter.setData(images)
        })
    }

    override fun onItemClick(item: Images) {
        val mapper = ImagesModelMapperImpl()
        val imageEntity: ImagesEntity = mapper.toEntity(item)
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageEntity)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(item: Images) {
        imagesViewModel.deleteData(item)
        toast(R.string.delete_image)
    }
}