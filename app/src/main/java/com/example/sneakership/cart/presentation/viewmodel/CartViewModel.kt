package com.example.sneakership.cart.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakership.cart.domain.model.CartTotal
import com.example.sneakership.cart.domain.repository.ICartRepository
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: ICartRepository
) : ViewModel(), ICartViewModelContract {


    /*Get Cart Items*/
    private val cartItems = MutableLiveData<Resource<List<Sneaker>>>()

    override fun getCartItems() {
        viewModelScope.launch {
            repo.getCartItems()
                .onStart {
                    cartItems.postValue(Resource.Loading)
                }
                .catch { e ->
                    cartItems.postValue(Resource.Failure(e.message))
                }
                .collect { list ->
                    if (list.isEmpty()) {
                        cartItems.postValue(Resource.NoResult)
                    } else {
                        cartItems.postValue(Resource.Success(list))
                    }
                }
        }
    }

    override fun observeCartItems(): LiveData<Resource<List<Sneaker>>> =
        cartItems

    /*Add Item to Cart*/
    override fun addItemToCart(sneaker: Sneaker) {
        viewModelScope.launch {
            repo.addItemToCart(sneaker)
        }
    }

    /*Delete Item from cart*/
    override fun deleteCartItem(sneaker: Sneaker) {
        viewModelScope.launch {
            repo.deleteCartItem(sneaker)
        }
    }

    /*clear all cart Items*/
    override fun removeAllCartItems() {
        viewModelScope.launch {
            repo.removeAllCartItems()
        }
    }

    /*Get Cart Total*/
    private val cartTotal = MutableLiveData<Resource<CartTotal>>()

    override fun getCartTotal() {
        viewModelScope.launch {
            repo.getCartTotal()
                .onStart {
                    cartTotal.postValue(Resource.Loading)
                }
                .catch { e ->
                    cartTotal.postValue(Resource.Failure(e.message))
                }
                .collect { list ->
                    cartTotal.postValue(Resource.Success(list))
                }
        }
    }

    override fun observeCartTotal(): LiveData<Resource<CartTotal>> =
        cartTotal
}