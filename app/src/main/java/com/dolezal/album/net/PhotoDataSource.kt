package com.dolezal.album.net

import com.dolezal.album.data.Photo
import com.dolezal.album.data.Upload
import io.reactivex.Single

interface PhotoDataSource {
    fun getPhotos(albumId: Long): Single<List<Photo>>
    fun uploadPhoto(title: String, albumId: Long): Single<Upload>
}