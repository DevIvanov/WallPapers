package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.data.database.ImagesEntity
import com.example.ivanov_p3.R
import com.example.ivanov_p3.ui.fragment.SearchFragmentDirections
import com.example.ivanov_p3.util.GoogleSearchAsyncTask
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class SearchGridViewAdapter(
    private var mContext: Context, var query: String,
    private val widthHeight: Int
): BaseAdapter() {

    private var imagesEntityList = GoogleSearchAsyncTask.imagesEntityList

    override fun getCount(): Int {
        return imagesEntityList.size
    }

    override fun getItem(position: Int): Any {
        return imagesEntityList[position]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
            imageView.layoutParams = LinearLayout.LayoutParams(widthHeight, widthHeight)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.load(imagesEntityList[position]?.link){
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_image)
            transformations(RoundedCornersTransformation(10f))
            listener(
                onError = { request: ImageRequest, throwable: Throwable ->
                    Log.e("COIL", "Error download")
                }
            )
        }

        imageView.setOnClickListener {
            val imageEntity = imagesEntityList[position]
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(imageEntity as ImagesEntity)
            action.query = query
            imageView.findNavController().navigate(action)
        }
        return imageView
    }
}