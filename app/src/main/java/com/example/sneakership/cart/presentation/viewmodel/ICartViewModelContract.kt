package com.example.sneakership.cart.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.sneakership.cart.domain.model.CartTotal
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.Resource

interface ICartViewModelContract {


    fun getCartItems()
    fun observeCartItems(): LiveData<Resource<List<Sneaker>>>

    fun addItemToCart(sneaker: Sneaker)

    fun deleteCartItem(sneaker: Sneaker)

    fun getCartTotal()
    fun observeCartTotal(): LiveData<Resource<CartTotal>>
    fun removeAllCartItems()
}