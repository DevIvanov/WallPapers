package com.example.ivanov_p3.ui.fragment.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouritesBinding
import com.example.ivanov_p3.ui.adapter.TabAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        adapter = TabAdapter(requireActivity())
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "TAB ${position + 1}"
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_bookmark)
                1 -> tab.setIcon(R.drawable.ic_image)
            }
        }.attach()

        backPressed()
        return binding.root
    }
}
