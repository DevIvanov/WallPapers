package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentHistoryBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.adapter.HistoryRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var adapter: HistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        adapter = HistoryRecyclerViewAdapter(
            mHistoryViewModel = mHistoryViewModel,
            mContext = requireContext()
        )

        setAdapter()
        readDataFromDatabase()

        backPressed()
        return binding.root
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDataFromDatabase() {
        mHistoryViewModel.readAllData.observe(viewLifecycleOwner, Observer { history ->
            adapter.setData(history)
        })
        binding.recyclerView.scheduleLayoutAnimation()
        Log.d("Database", "Adapter set")
    }
}