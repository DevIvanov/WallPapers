package com.example.ivanov_p3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.FavoriteGridItemBinding
import java.util.*


class FavouriteGridAdapter(
    private val itemListener: OnItemClickListener,
    private val deleteListener: OnDeleteClickListener
    ): RecyclerView.Adapter<FavouriteGridAdapter.MyViewHolder>(){

    private val imagesList: MutableList<Images> = LinkedList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(images: List<Images>) {
        imagesList.clear()
        imagesList.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FavoriteGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: FavoriteGridItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    itemListener.onItemClick(imagesList[position])
            }
            binding.fabDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    deleteListener.onDeleteClick(imagesList[position])
            }
        }

        fun bind() {
            binding.apply {
                Glide.with(itemView)
                    .load(imagesList[bindingAdapterPosition].urlRegular)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .placeholder(R.drawable.ic_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(image)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: Images)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(item: Images)
    }
}