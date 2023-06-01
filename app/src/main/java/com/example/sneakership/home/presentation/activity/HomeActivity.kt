package com.example.sneakership.home.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sneakership.R
import com.example.sneakership.databinding.ActivityHomeBinding
import com.example.sneakership.home.presentation.adapter.SneakersAdapter
import com.example.sneakership.home.presentation.viewmodel.HomeViewModel
import com.example.sneakership.utils.Resource
import com.example.sneakership.utils.UiUtils.showLogE
import com.example.sneakership.utils.UiUtils.showToast
import com.example.sneakership.utils.network.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var sneakersAdapter: SneakersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewClick()
        setupRecycler()
        observeGetSneakers()

        getSneakers()
    }

    private fun onViewClick() {
        binding.ivSearch.setOnClickListener {

        }
    }

    private fun setupRecycler() {
        sneakersAdapter = SneakersAdapter()

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvSneakers.apply {
            this.layoutManager = layoutManager
            adapter = sneakersAdapter
        }
    }

    private fun getSneakers() {
        if (NetworkUtils.hasInternetConnection(this)) {
            homeViewModel.getSneakers()
        } else {
            showToast(getString(R.string.check_connection))
        }
    }

    private fun observeGetSneakers() {
        homeViewModel.observeGetSneakers().observe(this) {
            it?.let { res ->
                when (res) {
                    is Resource.Loading -> {
                        showProgress(true)
                    }
                    is Resource.Success -> {
                        showProgress(show = false)
                        sneakersAdapter.addItems(res.result)
                    }
                    is Resource.Failure -> {
                        showProgress(show = false)
                        showLogE("observeGetSneakers :: ${res.message}")
                        showToast(res.message ?: "Error")
                    }
                    is Resource.NoResult -> {
                        showProgress(show = false)
                        showLogE(getString(R.string.no_result))
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