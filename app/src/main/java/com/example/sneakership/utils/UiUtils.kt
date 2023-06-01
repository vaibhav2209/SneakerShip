package com.example.sneakership.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

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

    fun Activity.hideKeyBoard() {
        val inputManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (this).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun Activity.showKeyBoard() {
        if (this.isFinishing) return
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}