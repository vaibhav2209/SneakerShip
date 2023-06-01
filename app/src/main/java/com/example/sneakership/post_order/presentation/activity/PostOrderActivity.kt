package com.example.sneakership.post_order.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sneakership.application.AppNavigationRoute
import com.example.sneakership.databinding.ActivityHomeBinding
import com.example.sneakership.databinding.ActivityPostOrderBinding

class PostOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostOrderBinding

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
}