package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.ui.fragment.favourite.FavouritesFragmentDirections


class FavouriteGridViewAdapter(private var mContext: Context,
private val imagesList: List<Images>): BaseAdapter() {

    override fun getCount(): Int {
        return imagesList.size
    }

    override fun getItem(position: Int): Any {
        return imagesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
            imageView.layoutParams = LinearLayout.LayoutParams(540, 540)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.load(imagesList[position].link){
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_image)
            transformations(RoundedCornersTransformation(10f))
        }

        imageView.setOnClickListener {
            val mapper = ImagesModelMapperImpl()
            val imageEntity: ImagesEntity = mapper.toEntity(imagesList[position])
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageEntity)
            imageView.findNavController().navigate(action)
        }
        return imageView
    }
}