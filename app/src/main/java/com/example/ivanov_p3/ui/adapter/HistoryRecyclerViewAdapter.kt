package com.example.ivanov_p3.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.RecyclerViewItemBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import com.example.ivanov_p3.ui.fragment.HistoryFragmentDirections
import com.example.ivanov_p3.util.Utils
import com.example.ivanov_p3.util.view.MyDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryRecyclerViewAdapter(
    private var historyList: List<History> = listOf(),
    private val mHistoryViewModel: HistoryViewModel,
    val mContext: Context,
    val fm: FragmentManager
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind()
    }

    inner class MyViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun onBind() {

            val currentItem: History = historyList[position]

            binding.nameTextView.text = currentItem.name

            val utils = Utils()
            val date = utils.dateWithMonthName(mContext, currentItem.date!!)

            binding.infoTextView.text = "${currentItem.count} ${mContext.getString(R.string.results)}, $date"
            if (currentItem.favourite) {
                binding.imageView.setImageResource(R.drawable.ic_favorite_blue)
            }else{
                binding.imageView.setImageResource(R.drawable.ic_favorite)
            }

            itemView.setOnClickListener {
                val currentQuery = currentItem.name.toString()
                val action = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment()
                action.currentQuery = currentQuery
                itemView.findNavController().navigate(action)
            }

            binding.imageView.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val favourite = !currentItem.favourite
                    val newItem = History(
                        currentItem.id,
                        currentItem.name,
                        currentItem.count,
                        currentItem.date,
                        favourite
                    )
                    mHistoryViewModel.updateData(newItem)
                }
            }

            itemView.setOnLongClickListener {
                val dialogItems = arrayOf(mContext.resources.getString(R.string.delete_one),
                mContext.resources.getString(R.string.delete_all))
                val myDialogFragment = MyDialogFragment(dialogItems, mHistoryViewModel, currentItem)
                myDialogFragment.show(fm, "myDialog")
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(history: List<History>) {
        this.historyList = history
        notifyDataSetChanged()
    }
}