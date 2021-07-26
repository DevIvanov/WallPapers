package com.example.ivanov_p3.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

        binding.apply {
            Glide.with(this@FullScreenFragment)
                .load(args.currentImage.urlFull)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarFullScreen.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarFullScreen.isVisible = false
                        return false
                    }
                })
                .into(imageView)
        }

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_FULLSCREEN)

        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.floatingActionButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = DEFAULT_BUFFER_SIZE
    }
}