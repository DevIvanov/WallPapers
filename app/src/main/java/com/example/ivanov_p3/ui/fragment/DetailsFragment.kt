package com.example.ivanov_p3.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.domain.model.Images
import com.example.ivanov_p3.R
import com.example.ivanov_p3.R.drawable
import com.example.ivanov_p3.R.layout
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentDetailsBinding
import com.example.ivanov_p3.ui.viewmodel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class DetailsFragment : BaseFragment(layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val mImagesViewModel: ImagesViewModel by viewModels()
    private lateinit var imageBitmap: Bitmap
    private var popupWindow: PopupWindow? = null
    private var popupWindowInfo: PopupWindow? = null


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        bindingApply()
        setText()
        return binding.root
    }

    private fun bindingApply() {
        var url = ""

        if (args.image.urlFull != null)
            url = args.image.urlFull.toString()

        binding.apply {
            Glide.with(this@DetailsFragment)
                .asBitmap()
                .load(url)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarDetails.isVisible = false
                        layoutError.isVisible = true
                        backView.setOnClickListener {
                            requireActivity().onBackPressed()
                        }
                        buttonRetry.setOnClickListener {
                            progressBarDetails.isVisible = true
                            layoutError.isVisible = false
                            bindingApply()
                        }
                        return false
                    }

                    @RequiresApi(Build.VERSION_CODES.Q)
                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarDetails.isVisible = false
                        onClick()
                        imageBitmap = resource as Bitmap
                        return false
                    }
                })
                .into(imgFavourite)
        }
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
        binding.fabFullScreen.setOnClickListener {
            val imageEntity = args.image
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

        val textName: TextView = popupView.findViewById(R.id.textName)
        textName.text = args.image.name

        val textDescription: TextView = popupView.findViewById(R.id.textDescription)
        textDescription.text = args.image.description

        val textDate: TextView = popupView.findViewById(R.id.textDate)
        var date = args.image.date
        date = date?.replaceRange(10, date.length, "")
        textDate.text = date

        val textColor: TextView = popupView.findViewById(R.id.textColor)
        textColor.text = args.image.color

        val width = args.image.width
        val height = args.image.height
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
        val image = Images(0, args.image.urlFull, args.image.urlRegular,
            args.image.date, args.image.width, args.image.height,
            args.image.color, args.image.name, args.image.description)
        mImagesViewModel.addData(image)
        val icon: Drawable = this.resources.getDrawable(drawable.ic_favorite)
        Toasty.normal(requireContext(), getString(R.string.add_to_favourite), icon)
        .show()
    }
}