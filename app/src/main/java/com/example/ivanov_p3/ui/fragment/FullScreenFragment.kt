package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import androidx.navigation.fragment.navArgs
import coil.load
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

        binding.imageView.load(args.currentImage.link)

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


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