package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.FavoriteGridItemBinding
import com.example.ivanov_p3.ui.ImagesViewModel
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

        fun bind() {
            binding.apply {
                Glide.with(itemView)
                    .load(imagesList[position].urlRegular)
                    .transforms(CenterCrop(), RoundedCorners(20))
                    .placeholder(R.drawable.ic_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(image)

                itemView.setOnClickListener {
                    val mapper = ImagesModelMapperImpl()
                    val imageEntity: ImagesEntity = mapper.toEntity(imagesList[position])
                    val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageEntity)
                    itemView.findNavController().navigate(action)
                }

                floatingActionButton2.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        mImagesViewModel.deleteData(imagesList[position])
                        Toast.makeText(mContext, R.string.delete_image, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(images: List<Images>) {
        this.imagesList = images
        notifyDataSetChanged()
    }
}