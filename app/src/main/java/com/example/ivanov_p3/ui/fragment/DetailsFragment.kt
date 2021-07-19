package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.R.drawable
import com.example.ivanov_p3.R.layout
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentDetailsBinding
import com.example.ivanov_p3.ui.ImagesViewModel
import com.example.ivanov_p3.util.Utils
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.String
import kotlin.properties.Delegates


class DetailsFragment : BaseFragment(layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var mImagesViewModel: ImagesViewModel
    private lateinit var imageBitmap: Bitmap
    private var popupWindow: PopupWindow? = null
    private var popupWindowInfo: PopupWindow? = null
    private var hex by Delegates.notNull<Int>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        binding.imageView.load(args.currentImage.link)

        MainScope().launch{
            val loader = ImageLoader(requireContext())
            val request = ImageRequest.Builder(requireContext())
                .data(args.currentImage.link)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            imageBitmap = (result as BitmapDrawable).bitmap
            hex = Palette.from(imageBitmap).generate().dominantSwatch?.rgb!!
        }


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
            binding.textViewToolbar.text = args.query
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
            shareImage(imageBitmap)
        }
        binding.settingView.setOnClickListener {
            popupWidowSettings()
        }
        binding.infoView.setOnClickListener {
            popupWidowInfo()
        }
    }

    private fun popupWidowSettings() {
        val inflater: LayoutInflater =
            binding.root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(layout.popup_window, null)
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
        popupWindow!!.setOnDismissListener { darkenBackground(1f) }
        popupWindow!!.isFocusable = true
        popupWindow!!.isOutsideTouchable = true
        darkenBackground(0.5f)


        popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)

        val buttonExit: Button = popupView.findViewById(R.id.buttonExit)
        buttonExit.setOnClickListener{
            popupWindow!!.dismiss()
        }

        val wallpaper: LinearLayoutCompat = popupView.findViewById(R.id.wallpaper)
        wallpaper.setOnClickListener {
            setWallpaper(imageBitmap)
            popupWindow!!.dismiss()
        }

        val splashScreen: LinearLayoutCompat = popupView.findViewById(R.id.splashScreen)
        splashScreen.setOnClickListener {
            setSplashScreen(imageBitmap)
            popupWindow!!.dismiss()
        }

        val favourite: LinearLayoutCompat = popupView.findViewById(R.id.favourite)
        favourite.setOnClickListener {
            addToFavourite()
            popupWindow!!.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun popupWidowInfo() {
        val inflater: LayoutInflater =
            binding.root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(layout.popup_window_info, null)
        val popupWidth = LinearLayout.LayoutParams.MATCH_PARENT
        val popupHeight = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        popupWindowInfo = PopupWindow(popupView, popupWidth, popupHeight)
        popupWindowInfo!!.update(
            0,
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            focusable
        )

        val textSearchLink: TextView = popupView.findViewById(R.id.textSearchLink)
        textSearchLink.text = args.currentImage.searchLink

        val textDate: TextView = popupView.findViewById(R.id.textDate)
        val utils = Utils()
        val date = utils.dateWithMonthName(requireContext(), args.currentImage.date!!)
        textDate.text = date

        val hexColor = String.format("#%06X", 0xFFFFFF and hex)
        val textColor: TextView = popupView.findViewById(R.id.textColor)
        textColor.text = hexColor

        val width = args.currentImage.width
        val height = args.currentImage.height
        val textDimens: TextView = popupView.findViewById(R.id.textDimens)
        textDimens.text = "Px: $width x $height"

        popupWindowInfo!!.setOnDismissListener { darkenBackground(1f) }
        popupWindowInfo!!.isFocusable = true
        popupWindowInfo!!.isOutsideTouchable = true
        darkenBackground(0.5f)

        popupWindowInfo!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)

        val buttonExit: Button = popupView.findViewById(R.id.exitButton)
        buttonExit.setOnClickListener{
            popupWindowInfo!!.dismiss()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addToFavourite() {
        val image = Images(0, args.currentImage.link, args.currentImage.date,
            args.currentImage.width, args.currentImage.height,
            hex, args.currentImage.searchLink)
        mImagesViewModel.addData(image)
        val icon: Drawable = this.resources.getDrawable(drawable.ic_favorite)
        Toasty.normal(requireContext(), getString(R.string.add_to_favourite), icon)
        .show()
    }
}