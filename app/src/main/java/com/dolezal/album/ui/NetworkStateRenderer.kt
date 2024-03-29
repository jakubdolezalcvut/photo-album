package com.dolezal.album.ui

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dolezal.album.R
import com.dolezal.album.data.NetworkLoading
import com.dolezal.album.data.NetworkState
import com.dolezal.album.data.NetworkSuccess
import com.dolezal.album.getDescription
import com.google.android.material.snackbar.Snackbar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class NetworkStateRenderer(
    private val containerView: View,
    private val refreshLayout: SwipeRefreshLayout,
    private val progressBar: MaterialProgressBar,
    private val emptyMessage: TextView
) {
    private var hasData = false

    fun render(state: NetworkState) {
        Log.i(TAG, state.toString())

        when (state) {
            NetworkLoading -> onLoading()
            NetworkSuccess -> onSuccess()
            is NetworkState.Error -> onError(state.throwable)
        }
    }

    private fun onLoading() {
        if (!refreshLayout.isRefreshing) {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun onSuccess() {
        hasData = true
        refreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        emptyMessage.visibility = View.GONE
    }

    private fun onError(throwable: Throwable) {
        refreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE

        if (!hasData) {
            emptyMessage.setText(R.string.network_empty_message)
            emptyMessage.visibility = View.VISIBLE
        }
        Snackbar.make(containerView, throwable.getDescription(), Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "NetworkStateRenderer"
    }
}