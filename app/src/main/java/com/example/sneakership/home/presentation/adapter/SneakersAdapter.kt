package com.example.sneakership.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakership.databinding.ItemRvSneakersBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.BaseViewHolder

class SneakersAdapter(
) : RecyclerView.Adapter<BaseViewHolder<Sneaker>>() {

    private val sourceItems = mutableListOf<Sneaker>()

    fun addItems(items: List<Sneaker>) {
        val count = if (items.isEmpty()) {
            0
        } else {
            itemCount
        }
        sourceItems.addAll(items)
        notifyItemRangeInserted(count, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Sneaker> {
        return SneakersViewHolder(
            binding = ItemRvSneakersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Sneaker>, position: Int) {
        holder.bind(sourceItems[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return sourceItems.size
    }
}