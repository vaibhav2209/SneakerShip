package com.example.sneakership.cart.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sneakership.databinding.ItemRvCartBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.BaseViewHolder

class CartAdapter(
    private val listener: CartAdapterListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.brand == newItem.brand
                    && oldItem.retailPrice == newItem.retailPrice
                    && oldItem.title == newItem.title
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(items: List<Sneaker>) = differ.submitList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            binding = ItemRvCartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(differ.currentList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CartViewHolder(val binding: ItemRvCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Sneaker) {

            binding.apply {

                tvPrice.text = "$${item.retailPrice}"

                tvSneakerName.text = item.name

                ivSneaker.load(item.sneakerImage.smallImageUrl)

                cvRemove.setOnClickListener {
                    listener.onItemRemove(item)
                }
            }
        }
    }
}