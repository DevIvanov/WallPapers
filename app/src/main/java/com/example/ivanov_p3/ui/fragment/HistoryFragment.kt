package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentHistoryBinding
import com.example.ivanov_p3.ui.ImagesViewModel

class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var mImagesViewModel: ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        mImagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)

        backPressed()
        return binding.root
    }
}