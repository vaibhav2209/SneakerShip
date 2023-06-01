package com.example.sneakership.sneaker_detail.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sneakership.R
import com.example.sneakership.databinding.ActivitySneakerDetailBinding
import com.example.sneakership.home.presentation.viewmodel.HomeViewModel
import com.example.sneakership.utils.Resource
import com.example.sneakership.utils.UiUtils
import com.example.sneakership.utils.UiUtils.showToast
import com.example.sneakership.utils.network.Constants
import com.example.sneakership.utils.network.NetworkUtils

class SneakerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySneakerDetailBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private var sneakerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySneakerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sneakerId = intent.getStringExtra(Constants.SNEAKER_ID).orEmpty()

        observeGetSneakers()

        getSneakerById(sneakerId)
    }

    private fun getSneakerById(id: String) {
        if (NetworkUtils.hasInternetConnection(this)) {
            homeViewModel.getSneakerById(id)
        } else {
            showToast(getString(R.string.check_connection))
        }
    }

    private fun observeGetSneakers() {
        homeViewModel.observeGetSneakerById().observe(this) {
            it?.let { res ->
                when (res) {
                    is Resource.Loading -> {
                        showProgress(true)
                    }
                    is Resource.Success -> {
                        showProgress(show = false)

                    }
                    is Resource.Failure -> {
                        showProgress(show = false)
                        UiUtils.showLogE("observeGetSneakerById :: ${res.message}")
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

    private fun showProgress(show: Boolean) {
        if (show)
            binding.progress.visibility = View.VISIBLE
        else
            binding.progress.visibility = View.GONE
    }
}