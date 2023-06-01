package com.example.sneakership.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast
import java.util.concurrent.TimeUnit

object UiUtils {

    private const val TAG = "SneakerShip => "

    fun Activity.showToast(message: String) {
        this.runOnUiThread {
            Toast.makeText(this@showToast, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showLogD(message: String) {
        Log.d(TAG, message)
    }

    fun showLogE(message: String?) {
        Log.e(TAG, message ?: "Error")
    }
}