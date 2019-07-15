package com.dolezal.album.vm

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolezal.album.data.*
import com.dolezal.album.net.PhotoDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Scope

class PhotoViewModel @RestrictTo(RestrictTo.Scope.TESTS) constructor(
    private val photoDataSource: PhotoDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _networkState = MutableLiveData<NetworkState>()
    private val _photos = MutableLiveData<List<Photo>>()

    val networkState: LiveData<NetworkState> = _networkState
    val photos: LiveData<List<Photo>> = _photos

    fun load(albumId: Long) {
        _networkState.postValue(NetworkLoading)

        photoDataSource.getPhotos(albumId).subscribeBy(
            onSuccess = { photoList ->
                _networkState.postValue(NetworkSuccess)
                _photos.postValue(photoList)
            },
            onError = { throwable ->
                _networkState.postValue(NetworkError(throwable))
            }
        ).also { disposable ->
            compositeDisposable.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        @SuppressLint("RestrictedApi")
        fun create(scope: Scope): PhotoViewModel {
            return PhotoViewModel(
                photoDataSource = scope.getDependency()
            )
        }
    }
}