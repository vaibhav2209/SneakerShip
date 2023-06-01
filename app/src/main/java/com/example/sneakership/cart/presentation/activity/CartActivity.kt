package com.example.sneakership.cart.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakership.R
import com.example.sneakership.application.AppNavigationRoute
import com.example.sneakership.cart.presentation.adapter.CartAdapter
import com.example.sneakership.cart.presentation.adapter.CartAdapterListener
import com.example.sneakership.cart.presentation.viewmodel.CartViewModel
import com.example.sneakership.databinding.ActivityCartBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.utils.Resource
import com.example.sneakership.utils.UiUtils
import com.example.sneakership.utils.UiUtils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity(), CartAdapterListener {


    private lateinit var binding: ActivityCartBinding

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewClick()
        setupRecycler()
        observeCartItems()
        observeCartTotal()
        setupBottomNavigationView()

        getCartItems()
        getCartTotal()
    }

    private fun onViewClick() {
        binding.btnCheckout.setOnClickListener {
            AppNavigationRoute.openPostOrderActivityAndKillOthers(this)
        }
    }

    private fun setupRecycler() {
        cartAdapter = CartAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCartItems.apply {
            this.layoutManager = layoutManager
            adapter = cartAdapter
        }
    }

    private fun setupBottomNavigationView() {

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.navigation_cart

        binding.bottomNavView.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_cart -> {
                    true
                }
                R.id.navigation_home -> {
                    AppNavigationRoute.openHomeActivity(this)
                    overridePendingTransition(0,0)
                    finishAfterTransition()
                    true
                }
                else -> false
            }
        }
    }

    private fun getCartItems() {
        cartViewModel.getCartItems()
    }

    private fun observeCartItems() {
        cartViewModel.observeCartItems().observe(this) {
            it?.let { res ->
                when (res) {
                    is Resource.Loading -> {
                        showProgress(true)
                    }
                    is Resource.Success -> {
                        showProgress(show = false)
                        showCartEmpty(false)
                        cartAdapter.submitList(res.result)
                    }
                    is Resource.Failure -> {
                        showProgress(show = false)
                        showCartEmpty(true)
                        UiUtils.showLogE("observeCartItems :: ${res.message}")
                        showToast(res.message ?: "Error")
                    }
                    is Resource.NoResult -> {
                        showProgress(show = false)
                        showCartEmpty(true)
                        UiUtils.showLogE(getString(R.string.no_result))
                    }
                }
            }
        }
    }

    private fun getCartTotal() {
        cartViewModel.getCartTotal()
    }

    private fun observeCartTotal() {
        cartViewModel.observeCartTotal().observe(this) {
            it?.let { res ->
                when (res) {
                    is Resource.Loading -> {
                        showProgress(true)
                    }
                    is Resource.Success -> {
                        showProgress(show = false)
                        calculateTotal(res.result.total)
                    }
                    is Resource.Failure -> {
                        showProgress(show = false)
                        UiUtils.showLogE("observeCartTotal :: ${res.message}")
                        showToast(res.message ?: "Error")
                    }
                    is Resource.NoResult -> {
                        showProgress(show = false)
                        UiUtils.showLogE(getString(R.string.no_result))
                    }
                }
            }
        }
    }

    private fun calculateTotal(subTotal: Int) {
        binding.tvSubTotal.text = getString(R.string.subtotal_placeholder, subTotal.toString())

        /*Assuming 5% as Taxes and charges*/
        val tax = (subTotal * 0.05).toInt()
        binding.tvTaxes.text = getString(R.string.texes_placeholder, tax.toString())

        val total = subTotal + tax
        binding.tvTotal.text = getString(R.string.price_placeholder, total.toString())
    }

    private fun removeCartItem(sneaker: Sneaker) {
        cartViewModel.deleteCartItem(sneaker)
        showToast("Item removed from cart")
    }

    override fun onItemRemove(sneaker: Sneaker) {
        removeCartItem(sneaker)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            binding.progress.visibility = View.VISIBLE
        else
            binding.progress.visibility = View.GONE
    }

    private fun showCartEmpty(show: Boolean) {
        if (show) {
            binding.tvEmptyCart.visibility = View.VISIBLE
            binding.constraintCart.visibility = View.GONE
        } else {
            binding.tvEmptyCart.visibility = View.GONE
            binding.constraintCart.visibility = View.VISIBLE
        }
    }
}