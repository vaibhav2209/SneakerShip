package com.example.sneakership.home.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.sneakership.R
import com.example.sneakership.application.AppNavigationRoute
import com.example.sneakership.cart.presentation.viewmodel.CartViewModel
import com.example.sneakership.databinding.ActivityHomeBinding
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.model.SortBy
import com.example.sneakership.home.presentation.adapter.SneakerAdapterListener
import com.example.sneakership.home.presentation.adapter.SneakersAdapter
import com.example.sneakership.home.presentation.viewmodel.HomeViewModel
import com.example.sneakership.utils.Resource
import com.example.sneakership.utils.UiUtils.hideKeyBoard
import com.example.sneakership.utils.UiUtils.showKeyBoard
import com.example.sneakership.utils.UiUtils.showLogE
import com.example.sneakership.utils.UiUtils.showToast
import com.example.sneakership.utils.network.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), SneakerAdapterListener {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var sneakersAdapter: SneakersAdapter

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewClick()
        setupRecycler()
        bindSearch()
        setupBottomNavigationView()
        setupRecyclerObserver()
        observeGetSneakers()

        getSneakers()
    }

    private fun onViewClick() {
        binding.ivSearch.setOnClickListener {
            showSearchBar(true)
            binding.etSearch.requestFocus()
            showKeyBoard()
        }

        binding.ivClose.setOnClickListener {
            showSearchBar(false)
            clearSearch()
            hideKeyBoard()
        }

        binding.tvSortBy.setOnClickListener {
            showSortByLayout(!binding.llSortBy.isVisible)
        }

        binding.tvRelevant.setOnClickListener {
            getSneakers(sortBy = SortBy.Relevant)
            showSortByLayout(false)
        }

        binding.tvPriceHighest.setOnClickListener {
            getSneakers(sortBy = SortBy.PriceHighest)
            showSortByLayout(false)
        }

        binding.tvPriceLowest.setOnClickListener {
            getSneakers(sortBy = SortBy.PriceLowest)
            showSortByLayout(false)
        }
    }

    private fun setupBottomNavigationView() {

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.navigation_home

        binding.bottomNavView.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_cart -> {
                    AppNavigationRoute.openCartActivity(this)
                    overridePendingTransition(0,0)
                    finishAfterTransition()
                    true
                }
                R.id.navigation_home -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun bindSearch() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            it?.let { editable ->
                job?.cancel()
                val query = editable.toString().trim()
                job = MainScope().launch {
                    getSneakers()

                    delay(500L)
                }
            }
        }
    }

    private fun clearSearch() {
        binding.etSearch.text?.clear()
    }

    private fun getSearchQuery() =
        binding.etSearch.text.toString().trim()


    private fun setupRecycler() {
        sneakersAdapter = SneakersAdapter(this)

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvSneakers.apply {
            this.layoutManager = layoutManager
            adapter = sneakersAdapter
        }
    }

    private fun getSneakers(sortBy: SortBy = SortBy.Relevant) {
        if (NetworkUtils.hasInternetConnection(this)) {
            homeViewModel.getSneaker(sortBy, getSearchQuery())
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
                        sneakersAdapter.submitList(res.result)
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

    private fun setupRecyclerObserver() {
        sneakersAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                showLogE("OnRecyclerUpdate :: onItemRangeInserted =>")
                binding.rvSneakers.smoothScrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                showLogE("OnRecyclerUpdate :: onItemRangeChanged =>")
                binding.rvSneakers.smoothScrollToPosition(0)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                showLogE("OnRecyclerUpdate :: onItemRangeMoved =>")
                binding.rvSneakers.smoothScrollToPosition(0)
            }
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                showLogE("OnRecyclerUpdate :: onItemRangeRemoved =>")
                binding.rvSneakers.smoothScrollToPosition(0)
            }
        })
    }

    private fun addToCartItem(sneaker: Sneaker) {
        cartViewModel.addItemToCart(sneaker)
        showToast("Item added to cart")
    }

    override fun onSneakerClick(sneakerId: String) {
        AppNavigationRoute.openSneakerDetailActivity(
            context = this,
            id = sneakerId
        )
    }

    override fun onAddToCart(sneaker: Sneaker) {
        addToCartItem(sneaker)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            binding.progress.visibility = View.VISIBLE
        else
            binding.progress.visibility = View.GONE
    }

    private fun showSearchBar(show: Boolean) {
        if (show)
            binding.cvSearch.visibility = View.VISIBLE
        else
            binding.cvSearch.visibility = View.GONE
    }

    private fun showSortByLayout(show: Boolean) {
        if (show)
            binding.llSortBy.visibility = View.VISIBLE
        else
            binding.llSortBy.visibility = View.GONE
    }
}