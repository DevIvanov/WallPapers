package com.example.ivanov_p3.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ivanov_p3.ui.fragment.favourite.FavouriteImageFragment
import com.example.ivanov_p3.ui.fragment.favourite.FavouriteQueryFragment


class TabAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment ?= null
        when (position) {
            0 -> fragment = FavouriteImageFragment()
            1 -> fragment = FavouriteQueryFragment()
        }
        return fragment!!
    }
}