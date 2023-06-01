package com.example.sneakership.home.presentation.adapter

import coil.load
import com.example.sneakership.databinding.ItemRvSneakersBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.BaseViewHolder

class SneakersViewHolder(
    private val binding: ItemRvSneakersBinding,
) : BaseViewHolder<Sneaker>(binding.root) {

    override fun bind(item: Sneaker) {

        binding.apply {

            tvPrice.text = "$${item.retailPrice}"

            tvSneakerName.text = item.name

            ivSneaker.load(item.sneakerImage.smallImageUrl)

            cvAddToCart.setOnClickListener {

            }

            root.setOnClickListener {

            }
        }
    }
}