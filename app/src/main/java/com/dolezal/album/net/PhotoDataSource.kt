package com.dolezal.album.net

import com.dolezal.album.data.Photo
import io.reactivex.Single

interface PhotoDataSource {
    fun getPhotos(albumId: Long): Single<List<Photo>>
}