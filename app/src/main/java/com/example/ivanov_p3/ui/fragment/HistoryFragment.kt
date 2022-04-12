package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentHistoryBinding
import com.example.ivanov_p3.ui.adapter.HistoryAdapter
import com.example.ivanov_p3.ui.viewmodel.HistoryViewModel
import com.example.ivanov_p3.util.view.MyDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment(R.layout.fragment_history),
    HistoryAdapter.OnItemClickListener,
    HistoryAdapter.OnItemLongClickListener,
    HistoryAdapter.OnFavouriteClickListener,
    MyDialogFragment.OnClickListenerDialog
{

    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        adapter = HistoryAdapter(
            itemListener = this,
            longListener = this,
            favouriteListener = this
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
        historyViewModel.readAllData.observe(viewLifecycleOwner, Observer { history ->
            adapter.setData(history)
        })
        binding.recyclerView.scheduleLayoutAnimation()
    }

    override fun onItemClick(item: History) {
        val currentQuery = item.name
        val action = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment()
        action.currentQuery = currentQuery
        findNavController().navigate(action)
    }

    override fun onItemLongClick(item: History) {
        historyViewModel.setItem(item)
        val myDialogFragment = MyDialogFragment(this)
        myDialogFragment.show(childFragmentManager, "myDialog")
    }

    override fun onFavouriteClick(item: History) {
        val favourite = !item.favourite
        val newItem = History(
            item.id,
            item.name,
            item.count,
            item.date,
            favourite
        )
        historyViewModel.updateData(newItem)
    }

    override fun onDialogClick(index: Int) {
        val itemsToSelect = arrayOf(resources.getString(R.string.delete_one),
            resources.getString(R.string.delete_all))
        val item = historyViewModel.item.value

        if (index == 0) {
            if (item != null)
            historyViewModel.deleteData(item)
            Toast.makeText(
                activity,
                itemsToSelect[index],
                Toast.LENGTH_SHORT
            ).show()
        }else {
            historyViewModel.deleteAllData()
            Toast.makeText(
                activity,
                itemsToSelect[index],
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}