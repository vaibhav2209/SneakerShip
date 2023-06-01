package com.example.sneakership.cart.domain.repository

import com.example.sneakership.cart.domain.model.CartTotal
import com.example.sneakership.home.domain.model.Sneaker
import kotlinx.coroutines.flow.Flow

interface ICartRepository {

    fun getCartItems(): Flow<List<Sneaker>>

    suspend fun addItemToCart(sneaker: Sneaker)

    suspend fun deleteCartItem(sneaker: Sneaker)

    fun getCartTotal(): Flow<CartTotal>
    fun removeAllCartItems()
}