package com.example.ivanov_p3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.RecyclerViewItemBinding
import com.example.ivanov_p3.util.Utils
import java.util.*


class HistoryAdapter(
    private val itemListener: OnItemClickListener,
    private val longListener: OnItemLongClickListener,
    private val favouriteListener: OnFavouriteClickListener
) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    private val historyList: MutableList<History> = LinkedList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(history: List<History>) {
        historyList.clear()
        historyList.addAll(history)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    inner class MyViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    itemListener.onItemClick(historyList[position])
            }
            itemView.setOnLongClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    longListener.onItemLongClick(historyList[position])

                return@setOnLongClickListener true
            }
            binding.imgFavourite.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    favouriteListener.onFavouriteClick(historyList[position])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            val currentItem: History = historyList[bindingAdapterPosition]

            binding.nameTextView.text = currentItem.name

            val utils = Utils()
            val date = utils.dateWithMonthName(itemView.context, currentItem.date)

            binding.infoTextView.text = "${currentItem.count} ${itemView.context.getString(R.string.results)}, $date"
            if (currentItem.favourite)
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_blue)
            else
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: History)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: History)
    }

    interface OnFavouriteClickListener {
        fun onFavouriteClick(item: History)
    }
}