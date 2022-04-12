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


class HistoryFavouriteAdapter(
    private val itemListener: OnItemClickListener,
    private val favouriteListener: OnFavouriteClickListener
) : RecyclerView.Adapter<HistoryFavouriteAdapter.MyViewHolder>() {

    private val favouriteList: MutableList<History> = LinkedList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<History>) {
        favouriteList.clear()
        favouriteList.addAll(list)
        notifyDataSetChanged()
    }

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

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    itemListener.onItemClick(favouriteList[position])
            }

            binding.imgFavourite.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                    favouriteListener.onFavouriteClick(favouriteList[position])
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBind() {
            val currentItem: History = favouriteList[bindingAdapterPosition]

            val utils = Utils()
            val date = utils.dateWithMonthName(itemView.context, currentItem.date)

            if (currentItem.favourite) {
                binding.nameTextView.text = currentItem.name
                binding.infoTextView.text = "${currentItem.count} ${itemView.context.getString(R.string.results)}, $date"
                binding.imgFavourite.setImageResource(R.drawable.ic_delete)
            }
        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    interface OnFavouriteClickListener {
        fun onFavouriteClick(item: History)
    }

    interface OnItemClickListener {
        fun onItemClick(item: History)
    }
}