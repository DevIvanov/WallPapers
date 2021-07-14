package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.floatingActionButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}