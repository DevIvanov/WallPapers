package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.data.database.ImagesEntity
import com.example.data.mapper.ImagesModelMapperImpl
import com.example.domain.model.Images
import com.example.ivanov_p3.ui.fragment.favourite.FavouritesFragmentDirections
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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
            imageView.layoutParams = LinearLayout.LayoutParams(400, 600)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }
        val imageString: String? = imagesList[position].bitmap
        val imageBitmap: Bitmap?

//        if (imageString == null){
//            val imageLink = imagesList[position].link
//            imageBitmap = getBitmapFromURL(imageLink)
//        }else{
            imageBitmap = decodePhoto(imageString)
//        }

        imageView.setImageBitmap(imageBitmap)

        imageView.setOnClickListener {
            val mapper = ImagesModelMapperImpl()
            val imageEntity: ImagesEntity = mapper.toEntity(imagesList[position])
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(imageEntity)
            imageView.findNavController().navigate(action)
        }
        return imageView
    }

    private fun decodePhoto(encodedString: String?): Bitmap? {
        Log.d("LOG", "encodedString = " + encodedString.toString())
        val decodedString: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url
                .openConnection() as HttpURLConnection
            connection.doInput = true
//            try {
                connection.connect()
//            } catch (e: IOException){
//                Log.d("LOG", e.message.toString())
//            }
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}