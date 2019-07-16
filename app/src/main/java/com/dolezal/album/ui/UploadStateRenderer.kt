package com.dolezal.album.ui

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.dolezal.album.R
import com.dolezal.album.data.NetworkLoading
import com.dolezal.album.data.NetworkState
import com.dolezal.album.data.NetworkSuccess
import com.dolezal.album.getDescription

class UploadStateRenderer(
    private val button: Button,
    private val onNavigatingBack: () -> Unit
) {
    private val context = button.context

    fun render(state: NetworkState) {
        Log.i(TAG, state.toString())

        when (state) {
            NetworkLoading -> onLoading()
            NetworkSuccess -> onSuccess()
            is NetworkState.Error -> onError(state.throwable)
        }
    }

    private fun onLoading() {
        button.isEnabled = false
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(button.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun onSuccess() {
        button.isEnabled = true
        onNavigatingBack()
        Toast.makeText(context, R.string.upload_success, Toast.LENGTH_SHORT).show()
    }

    private fun onError(throwable: Throwable) {
        button.isEnabled = true
        Toast.makeText(context, throwable.getDescription(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "UploadStateRenderer"
    }
}