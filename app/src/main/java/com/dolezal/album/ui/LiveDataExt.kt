package com.dolezal.album.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import toothpick.Scope
import toothpick.Toothpick

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> Fragment.getViewModel(crossinline factory: (Scope) -> VM): VM {
    val scope = Toothpick.openScope(activity?.applicationContext)

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return factory(scope) as VM
        }
    }
    return ViewModelProviders.of(this, vmFactory).get(VM::class.java)
}