package com.example.sneakership.cart.data.repository

import com.example.sneakership.cart.data.local.ISneakerDao
import com.example.sneakership.cart.domain.model.CartTotal
import com.example.sneakership.cart.domain.repository.ICartRepository
import com.example.sneakership.cart.domain.toSneaker
import com.example.sneakership.cart.domain.toSneakerEntity
import com.example.sneakership.home.data.remote.ISneakerApi
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.repository.ISneakerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: ISneakerDao
) : ICartRepository {

    override fun getCartItems(): Flow<List<Sneaker>> =
        dao.getCartItems()
            .map { it.map { s -> s.toSneaker() }}

    override fun getCartTotal(): Flow<CartTotal> =
        dao.getCartTotal()

    override suspend fun addItemToCart(sneaker: Sneaker) =
        dao.addItemToCart(sneaker.toSneakerEntity())

    override suspend fun deleteCartItem(sneaker: Sneaker) =
        dao.deleteCartItem(sneaker.toSneakerEntity())
}