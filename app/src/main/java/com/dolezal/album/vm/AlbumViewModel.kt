package com.dolezal.album.vm

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolezal.album.data.*
import com.dolezal.album.net.AlbumDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Scope

class AlbumViewModel @RestrictTo(RestrictTo.Scope.TESTS) constructor(
    private val albumDataSource: AlbumDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _networkState = MutableLiveData<NetworkState>()
    private val _albums = MutableLiveData<List<Album>>()

    val networkState: LiveData<NetworkState> = _networkState
    val albums: LiveData<List<Album>> = _albums

    fun load() {
        _networkState.postValue(NetworkLoading)

        albumDataSource.getAlbums().subscribeBy(
            onSuccess = { albumList ->
                _networkState.postValue(NetworkSuccess)
                _albums.postValue(albumList)
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
        fun create(scope: Scope): AlbumViewModel {
            return AlbumViewModel(
                albumDataSource = scope.getDependency()
            )
        }
    }
}
