package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.ivanov_p3.util.view.GoogleSearchAsyncTask
import com.example.ivanov_p3.ui.fragment.SearchFragmentDirections
import java.io.ByteArrayOutputStream


class SearchGridViewAdapter(private var mContext: Context): BaseAdapter() {

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
//            imageView.setLayoutParams(AbsListView.LayoutParams(100, 150))
            imageView.layoutParams = LinearLayout.LayoutParams(400, 600)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }
        imageView.setImageBitmap(arrayBitmap[position])

        val imageBitmap = arrayBitmap[position]
        val imageString: String = encodePhoto(imageBitmap).toString()
        imageView.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(imageString)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodePhoto(photo: Bitmap?): String? {
        val bos = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val byteArray: ByteArray = bos.toByteArray()
        return java.util.Base64.getEncoder().encodeToString(byteArray)
    }

}