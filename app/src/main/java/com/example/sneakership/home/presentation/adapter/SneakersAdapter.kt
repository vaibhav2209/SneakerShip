package com.example.sneakership.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sneakership.application.AppNavigationRoute
import com.example.sneakership.cart.presentation.adapter.CartAdapter
import com.example.sneakership.databinding.ItemRvSneakersBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.BaseViewHolder
import java.util.*

class SneakersAdapter(
    private val listener: SneakerAdapterListener
) : RecyclerView.Adapter<SneakersAdapter.SneakersViewHolder>() {

    private val sourceItems = mutableListOf<Sneaker>()

    private var filteredItem = mutableListOf<Sneaker>()

    fun addItems(items: List<Sneaker>) {
        sourceItems.addAll(items)
        filteredItem.clear()
        filteredItem.addAll(sourceItems)
        notifyItemRangeInserted(0, filteredItem.size)
    }

    fun filter(query: String) {
        filteredItem = if (query.isNotEmpty()) {
            sourceItems.filter { item ->
                item.name.contains(query, ignoreCase = true)
            }.toMutableList()
        } else {
            sourceItems
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakersViewHolder {
        return SneakersViewHolder(
            binding = ItemRvSneakersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SneakersViewHolder, position: Int) {
        holder.bind(filteredItem[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return filteredItem.size
    }


    inner class SneakersViewHolder(
        private val binding: ItemRvSneakersBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sneaker) {

            binding.apply {

                tvPrice.text = "$${item.retailPrice}"

                tvSneakerName.text = item.name

                ivSneaker.load(item.sneakerImage.smallImageUrl)

                cvAddToCart.setOnClickListener {
                    listener.onAddToCart(item)
                }

                root.setOnClickListener {
                    listener.onSneakerClick(item.id)
                }
            }
        }
    }
}