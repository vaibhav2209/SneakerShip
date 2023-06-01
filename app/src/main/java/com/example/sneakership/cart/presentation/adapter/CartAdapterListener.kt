package com.example.sneakership.cart.presentation.adapter

import com.example.sneakership.home.domain.model.Sneaker

interface CartAdapterListener {

    fun onItemRemove(sneaker: Sneaker)
}