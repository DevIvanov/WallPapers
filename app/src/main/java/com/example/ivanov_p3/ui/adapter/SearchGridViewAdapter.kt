package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.data.database.ImagesEntity
import com.example.domain.model.Images
import com.example.ivanov_p3.ui.fragment.SearchFragmentDirections
import com.example.ivanov_p3.util.view.GoogleSearchAsyncTask
import java.io.ByteArrayOutputStream


class SearchGridViewAdapter(private var mContext: Context): BaseAdapter() {

    private var bitmapList = GoogleSearchAsyncTask.bitmapList

    override fun getCount(): Int {
        return bitmapList.size
    }

    override fun getItem(position: Int): Any {
        return bitmapList[position]!!
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
        imageView.setImageBitmap(bitmapList[position])

//        val imageBitmap = bitmapList[position]
//        val imageString: String = encodePhoto(imageBitmap).toString()

        imageView.setOnClickListener {
            val imageEntity = GoogleSearchAsyncTask.imagesEntityList[position]
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(imageEntity as ImagesEntity)
            imageView.findNavController().navigate(action)
        }
        return imageView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodePhoto(photo: Bitmap?): String? {
        val bos = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val byteArray: ByteArray = bos.toByteArray()
        return java.util.Base64.getEncoder().encodeToString(byteArray)
    }

}