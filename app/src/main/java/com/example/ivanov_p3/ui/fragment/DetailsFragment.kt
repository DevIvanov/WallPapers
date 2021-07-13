package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu

import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentDetailsBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.util.view.MyDialogFragment
import es.dmoral.toasty.Toasty
import java.io.File
import java.io.FileOutputStream


class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var imageBitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        imageBitmap = decodePhoto(args.currentImage.bitmap)!!
        binding.imageView.setImageBitmap(imageBitmap)
//        binding.webView.visibility = View.INVISIBLE
//        val link: String = args.currentImage.link.toString()
//        binding.webView.loadUrl(link)

        doubleTap()
        onClick()

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
//            }else{
//                showAlertDialog("Select action", "delete or not")
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun addToFavourite() {
        val image = Images(0, args.currentImage.bitmap, args.currentImage.link)
        mImagesViewModel.addData(image)
        val icon: Drawable = this.resources.getDrawable(R.drawable.ic_favorite)
        Toasty.normal(requireContext(), "Add to favourite!", icon).show()
    }

    private fun onClick() {
        binding.floatingActionButton.setOnClickListener {
            val imageEntity = args.currentImage
            val action = DetailsFragmentDirections.actionDetailsFragmentToFullScreenFragment(imageEntity)
            findNavController().navigate(action)
        }
        binding.shareView.setOnClickListener {
            shareImage()
        }


        val popupMenu2 = PopupMenu(requireContext(), binding.textView)
        popupMenu2.inflate(R.menu.popup_menu)
        popupMenu2.gravity = Gravity.CENTER_HORIZONTAL
        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
//                R.id.red -> {
//                    textView.background = ColorDrawable(Color.RED)
//                    textView.text = "Вы выбрали красный цвет"
//                }
//                R.id.yellow -> {
//                    textView.background = ColorDrawable(Color.YELLOW)
//                    textView.text = "Вы выбрали жёлтый цвет"
//                }
//                R.id.green -> {
//                    textView.background = ColorDrawable(Color.GREEN)
//                    textView.text = "Вы выбрали зелёный цвет"
//                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        }

        binding.settingView.setOnClickListener {
            popupMenu2.show()
        }
        binding.infoView.setOnClickListener {

            val itemsToSelect = arrayOf(
                getString(R.string.set_wallpaper),
                getString(R.string.set_splash_screen),
                getString(R.string.save_to_favourite)
            )
            val myDialogFragment = MyDialogFragment(itemsToSelect)
            val manager = requireActivity().supportFragmentManager
            myDialogFragment.show(manager, "myDialog")

        }

    }

    private fun shareImage() {
        try {
            val file = File(requireActivity().externalCacheDir,"image.png")
            val fOut = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val imageUri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.ivanov_p3.ui.fragment.DetailsFragment.provider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, imageUri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share image via"))

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LOG", e.message.toString())
        }
    }
}