package com.example.ivanov_p3.ui.fragment.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentFavouriteQueryBinding
import com.example.ivanov_p3.ui.adapter.HistoryFavouriteAdapter
import com.example.ivanov_p3.ui.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteQueryFragment : BaseFragment(R.layout.fragment_favourite_query),
    HistoryFavouriteAdapter.OnItemClickListener,
    HistoryFavouriteAdapter.OnFavouriteClickListener{

    private lateinit var binding: FragmentFavouriteQueryBinding
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryFavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteQueryBinding.inflate(layoutInflater, container, false)

        adapter = HistoryFavouriteAdapter(
            itemListener = this,
            favouriteListener = this
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
        historyViewModel.readAllData.observe(viewLifecycleOwner, Observer { history ->
            val favouriteList = history.filter { it.favourite }
            adapter.setData(favouriteList)
        })
        binding.recyclerView.scheduleLayoutAnimation()
        Log.i("Database", "Adapter set")
    }

    override fun onItemClick(item: History) {
        val currentQuery = item.name.toString()
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToSearchFragment()
        action.currentQuery = currentQuery
        findNavController().navigate(action)
    }

    override fun onFavouriteClick(item: History) {
        val newItem = History(
            item.id,
            item.name,
            item.count,
            item.date,
            !item.favourite
        )
        historyViewModel.updateData(newItem)
    }
}