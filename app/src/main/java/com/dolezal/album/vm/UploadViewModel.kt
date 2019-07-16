package com.dolezal.album.vm

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolezal.album.data.NetworkError
import com.dolezal.album.data.NetworkLoading
import com.dolezal.album.data.NetworkState
import com.dolezal.album.data.NetworkSuccess
import com.dolezal.album.net.PhotoDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Scope

class UploadViewModel @RestrictTo(RestrictTo.Scope.TESTS) constructor(
    private val photoDataSource: PhotoDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    fun upload(title: String, albumId: Long) {
        _networkState.postValue(NetworkLoading)

        photoDataSource.uploadPhoto(title, albumId).subscribeBy(
            onSuccess = { outcome ->
                _networkState.postValue(NetworkSuccess)
            },
            onError = { throwable ->
                _networkState.postValue(NetworkError(throwable))
            }
        ).also { disposable ->
            compositeDisposable.add(disposable)
        }
    }

    companion object {
        @SuppressLint("RestrictedApi")
        fun create(scope: Scope): UploadViewModel {
            return UploadViewModel(
                photoDataSource = scope.getDependency()
            )
        }
    }
}