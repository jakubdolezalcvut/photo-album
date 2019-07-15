package com.dolezal.album

import com.dolezal.album.net.*
import toothpick.config.Module

object AlbumToothpickModule : Module() {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    init {
        val albumService = AlbumServiceFactory.create(BASE_URL)
        val albumDataSource = RetrofitAlbumDataSource(albumService)
        val photoDataSource = RetrofitPhotoDataSource(albumService)

        bind(AlbumDataSource::class.java).toInstance(albumDataSource)
        bind(PhotoDataSource::class.java).toInstance(photoDataSource)
    }
}