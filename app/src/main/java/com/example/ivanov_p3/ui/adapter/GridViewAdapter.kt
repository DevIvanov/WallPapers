package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.ivanov_p3.R


class GridViewAdapter(private var mContext: Context,
                    ): BaseAdapter() { //private var arrayBitmap: ArrayList<Bitmap?>?


    override fun getCount(): Int {
        return arrayBitmap!!.size
    }

    override fun getItem(position: Int): Any {
        return arrayBitmap!![position]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = ImageView(mContext)
            imageView.setLayoutParams(AbsListView.LayoutParams(85, 85))
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(arrayBitmap[position])//setImageBitmap
        return imageView
    }

    var arrayBitmap = arrayOf<Int>(
        R.drawable.ic_favorite, R.drawable.ic_history,
        R.drawable.ic_search
    )
}