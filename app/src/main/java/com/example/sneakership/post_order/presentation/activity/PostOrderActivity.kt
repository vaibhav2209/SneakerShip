package com.example.sneakership.post_order.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.sneakership.application.AppNavigationRoute
import com.example.sneakership.cart.presentation.viewmodel.CartViewModel
import com.example.sneakership.databinding.ActivityHomeBinding
import com.example.sneakership.databinding.ActivityPostOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostOrderBinding

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewClick()
    }

    private fun onViewClick() {
        binding.btnHome.setOnClickListener {
            AppNavigationRoute.openHomeActivityAndKillOthers(this)
        }
    }

    private fun removeAllCartItems() {
        cartViewModel.removeAllCartItems()
    }
}