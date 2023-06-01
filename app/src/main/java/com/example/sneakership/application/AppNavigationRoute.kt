package com.example.sneakership.application

import android.content.Context
import android.content.Intent
import com.example.sneakership.cart.presentation.activity.CartActivity
import com.example.sneakership.home.presentation.activity.HomeActivity
import com.example.sneakership.post_order.presentation.activity.PostOrderActivity
import com.example.sneakership.sneaker_detail.presentation.activity.SneakerDetailActivity
import com.example.sneakership.utils.network.Constants

object AppNavigationRoute {

    fun openSneakerDetailActivity(context: Context, id: String) {
        context.startActivity(Intent(context, SneakerDetailActivity::class.java).apply {
            putExtra(Constants.SNEAKER_ID, id)
        })
    }

    fun openHomeActivity(context: Context) {
        context.startActivity(Intent(context, HomeActivity::class.java))
    }

    fun openCartActivity(context: Context) {
        context.startActivity(Intent(context, CartActivity::class.java))
    }

    fun openPostOrderActivityAndKillOthers(context: Context) {
        val intent = Intent(context, PostOrderActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun openHomeActivityAndKillOthers(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun openCartActivityAndKillOthers(context: Context) {
        val intent = Intent(context, CartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}