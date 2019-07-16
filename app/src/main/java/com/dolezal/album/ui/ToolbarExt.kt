package com.dolezal.album.ui

import android.content.res.Configuration
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.dolezal.album.R

fun Toolbar.setupForNavigation(onNavigatingBack: () -> Unit) {
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        visibility = View.GONE
    } else {
        setNavigationOnClickListener { _ ->
            onNavigatingBack()
        }
        navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_close)
    }
}