package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.domain.model.Images
import com.example.ivanov_p3.ui.fragment.FavouritesFragmentDirections


class FavouriteGridViewAdapter(private var mContext: Context,
private val arrayBitmap: List<Images>): BaseAdapter() {

    override fun getCount(): Int {
        return arrayBitmap.size
    }

    override fun getItem(position: Int): Any {
        return arrayBitmap[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
            imageView.layoutParams = LinearLayout.LayoutParams(400, 600)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }
        val imageString: String? = arrayBitmap[position].bitmap
        val imageBitmap: Bitmap? = decodePhoto(imageString)

        imageView.setImageBitmap(imageBitmap)

        imageView.setOnClickListener {
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageString.toString())
            imageView.findNavController().navigate(action)
        }
        return imageView
    }

    private fun decodePhoto(encodedString: String?): Bitmap? {
        val decodedString: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }
}