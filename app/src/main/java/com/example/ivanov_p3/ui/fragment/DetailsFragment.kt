package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentDetailsBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import es.dmoral.toasty.Toasty

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var mImagesViewModel: ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        val image = decodePhoto(args.currentImage.bitmap)
        binding.imageView.setImageBitmap(image)
        doubleTap()
        longClick()

        return binding.root
    }


    private fun decodePhoto(encodedString: String?): Bitmap? {
        val decodedString: ByteArray = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun doubleTap() {
        var pressedTime: Long = 0
        binding.imageView.setOnClickListener {

            if (pressedTime + 1000 > System.currentTimeMillis()) {
                addToFavourite()
                val icon: Drawable = this.resources.getDrawable(R.drawable.ic_favorite)
                Toasty.normal(requireContext(), "Add to favourite!", icon).show()
//            }else{
//                showAlertDialog("Select action", "delete or not")
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun addToFavourite() {
        val image = Images(0, args.currentImage.bitmap, args.currentImage.link)
        mImagesViewModel.addData(image)
        toast(args.currentImage.link.toString())
    }

    private fun longClick() {
        binding.imageView.setOnLongClickListener {
//            VibrationEffect.EFFECT_HEAVY_CLICK
//            Vibrator.VIBRATION_EFFECT_SUPPORT_YES
            showAlertDialog("Select action", "delete or not")
            true
        }
    }
}