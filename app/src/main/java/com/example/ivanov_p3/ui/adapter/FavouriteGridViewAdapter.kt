package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.History
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.FavoriteGridItemBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.ui.fragment.favourite.FavouriteImageFragment
import com.example.ivanov_p3.ui.fragment.favourite.FavouritesFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouriteGridViewAdapter(
    private var imagesList: List<Images> = listOf(),
    private val mImagesViewModel: ImagesViewModel,
    val mContext: Context
    ): RecyclerView.Adapter<FavouriteGridViewAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FavoriteGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: FavoriteGridItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun onBind() {

            binding.image.load(imagesList[position].link){
                crossfade(true)
                crossfade(1000)
                placeholder(R.drawable.ic_image)
                transformations(RoundedCornersTransformation(10f))
            }

            itemView.setOnClickListener {
                val mapper = ImagesModelMapperImpl()
                val imageEntity: ImagesEntity = mapper.toEntity(imagesList[position])
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageEntity)
                itemView.findNavController().navigate(action)
            }

            binding.floatingActionButton2.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mImagesViewModel.deleteData(imagesList[position])
                }
//                Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(images: List<Images>) {
        this.imagesList = images
        notifyDataSetChanged()
    }
}