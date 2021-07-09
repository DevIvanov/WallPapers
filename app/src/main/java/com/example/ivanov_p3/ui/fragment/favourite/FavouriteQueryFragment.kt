package com.example.ivanov_p3.ui.fragment.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouriteImageBinding
import com.example.ivanov_p3.databinding.FragmentFavouriteQueryBinding
import com.example.ivanov_p3.databinding.FragmentHistoryBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.adapter.HistoryRecyclerViewAdapter

class FavouriteQueryFragment : BaseFragment(R.layout.fragment_favourite_query) {

    private lateinit var binding: FragmentFavouriteQueryBinding
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var adapter: HistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteQueryBinding.inflate(layoutInflater, container, false)
        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        adapter = HistoryRecyclerViewAdapter(
            context = requireContext()
        )

        setAdapter()
        readDataFromDatabase()


        return  binding.root
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDataFromDatabase() {
        mHistoryViewModel.readAllData.observe(viewLifecycleOwner, Observer { history ->
            adapter.setData(history)
            binding.recyclerView.scheduleLayoutAnimation()
        })
        Log.d("Database", "Adapter set")
    }

}