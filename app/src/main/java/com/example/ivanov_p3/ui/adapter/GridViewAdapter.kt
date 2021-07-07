package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ivanov_p3.R
import com.example.ivanov_p3.ui.fragment.GoogleSearchAsyncTask


class GridViewAdapter(private var mContext: Context): BaseAdapter() {

    private var arrayBitmap = GoogleSearchAsyncTask.bitmapList

    override fun getCount(): Int {
        return arrayBitmap.size
    }

    override fun getItem(position: Int): Any {
        return arrayBitmap[position]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = ImageView(mContext)
//            imageView.setLayoutParams(AbsListView.LayoutParams(100, 150))
            imageView.layoutParams = LinearLayout.LayoutParams(400, 600)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageBitmap(arrayBitmap[position])
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