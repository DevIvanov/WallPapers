package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentDetailsBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import es.dmoral.toasty.Toasty
import java.io.File
import java.io.FileOutputStream


class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var imageBitmap: Bitmap
    private var popupWindow: PopupWindow? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        imageBitmap = decodePhoto(args.currentImage.bitmap)!!
        binding.imageView.setImageBitmap(imageBitmap)
//        if (args.currentImage)
//        binding.webView.visibility = View.INVISIBLE
//        val link: String = args.currentImage.link.toString()
//        binding.webView.loadUrl(link)
        setText()
        onClick()

        return binding.root
    }

    private fun setText(){
        if(args.query != null)
            binding.textViewToolbar.setText(args.query)
    }

    private fun decodePhoto(encodedString: String?): Bitmap? {
        val decodedString: ByteArray = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun onClick() {
        binding.backView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.floatingActionButton.setOnClickListener {
            val imageEntity = args.currentImage
            val action = DetailsFragmentDirections.actionDetailsFragmentToFullScreenFragment(imageEntity)
            findNavController().navigate(action)
        }
        binding.shareView.setOnClickListener {
            shareImage()
        }
        binding.settingView.setOnClickListener {
            popupWidowSettings()
        }
        binding.infoView.setOnClickListener {

        }

    }


    private fun popupWidowSettings() {
        val inflater: LayoutInflater =
            binding.root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)
        val popupWidth = LinearLayout.LayoutParams.MATCH_PARENT
        val popupHeight = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        popupWindow = PopupWindow(popupView, popupWidth, popupHeight)
        popupWindow!!.update(
            0,
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            focusable
        )
        popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)

        val buttonExit: Button = popupView.findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener{
            popupWindow!!.dismiss()
        }

        val wallpaper: LinearLayoutCompat = popupView.findViewById(R.id.wallpaper)
        wallpaper.setOnClickListener {
            setWallpaper()
            popupWindow!!.dismiss()
        }

        val splashScreen: LinearLayoutCompat = popupView.findViewById(R.id.splashScreen)
        splashScreen.setOnClickListener {
            setSplashScreen()
            popupWindow!!.dismiss()
        }

        val favourite: LinearLayoutCompat = popupView.findViewById(R.id.favourite)
        favourite.setOnClickListener {
            addToFavourite()
            popupWindow!!.dismiss()
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

    private fun setWallpaper() {
        val wallpaperManager = WallpaperManager.getInstance(requireActivity())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(
                imageBitmap,
                null,
                true,
                WallpaperManager.FLAG_SYSTEM
            )
            toast("Wallpaper set!")
        } else {
            toast("Unsupported version!")
        }
    }

    private fun setSplashScreen() {
        val wallpaperManager = WallpaperManager.getInstance(requireActivity())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(
                imageBitmap,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )
            toast("Splash screen set!")
        } else {
            toast("Unsupported version!")
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addToFavourite() {
        val image = Images(0, args.currentImage.bitmap, args.currentImage.link)
        mImagesViewModel.addData(image)
        val icon: Drawable = this.resources.getDrawable(R.drawable.ic_favorite)
        Toasty.normal(requireContext(), "Add to favourite!", icon)//.setGravity(Gravity.CENTER, 0, 0)
        .show()
    }

    override fun onPause() {
        super.onPause()
        if (popupWindow != null)
            popupWindow!!.dismiss()
    }
}