package com.example.ivanov_p3.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.Temp
import com.example.ivanov_p3.databinding.RecyclerViewItemBinding
import com.example.ivanov_p3.ui.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFavouriteRecyclerViewAdapter(
    private var favouriteList: List<History> = listOf(),
    private val mHistoryViewModel: HistoryViewModel,
    val context: Context
) : RecyclerView.Adapter<HistoryFavouriteRecyclerViewAdapter.MyViewHolder>() {

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

        fun onBind() {

            val currentItem: History = favouriteList[position]

            if (currentItem.favourite) {
                binding.nameTextView.text = currentItem.name
                binding.infoTextView.text = "${currentItem.count} results, ${currentItem.date}"
                binding.imageView.setImageResource(R.drawable.ic_favorite_blue)


                itemView.setOnClickListener {
                    Toast.makeText(context, "gewg", Toast.LENGTH_SHORT).show()
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
            }
        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    fun setData(history: List<History>) {
        this.favouriteList = history
        notifyDataSetChanged()
    }
}