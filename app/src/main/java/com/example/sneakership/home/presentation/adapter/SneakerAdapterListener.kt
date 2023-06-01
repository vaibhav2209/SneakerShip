package com.example.sneakership.home.presentation.adapter

import com.example.sneakership.home.domain.model.Sneaker

interface SneakerAdapterListener {

    fun onSneakerClick(sneakerId: String)

    fun onAddToCart(sneaker: Sneaker)
}