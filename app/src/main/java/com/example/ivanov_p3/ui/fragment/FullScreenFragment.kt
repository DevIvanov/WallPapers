package com.example.ivanov_p3.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFullScreenBinding


class FullScreenFragment : BaseFragment(R.layout.fragment_full_screen) {

    private lateinit var binding: FragmentFullScreenBinding
    private val args by navArgs<FullScreenFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenBinding.inflate(layoutInflater, container, false)

        val image = decodePhoto(args.currentImage.bitmap)
        binding.imageView.setImageBitmap(image)

        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.floatingActionButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun decodePhoto(encodedString: String?): Bitmap? {
        val decodedString: ByteArray = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }
}