package com.dolezal.album.net

import com.dolezal.album.data.Photo
import com.dolezal.album.data.Upload
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitPhotoDataSource(
    private val albumService: AlbumService
) : PhotoDataSource {

    override fun getPhotos(albumId: Long): Single<List<Photo>> {
        return albumService.getPhotos(albumId)
            .subscribeOn(Schedulers.io())
            .map { dtoList ->
                dtoList.map { dto -> dto.toDomain() }
            }
    }

    override fun uploadPhoto(title: String, albumId: Long): Single<Upload> {
        return albumService.uploadPhoto(title, albumId)
            .subscribeOn(Schedulers.io())
            .map { dto ->
                dto.toDomain()
            }
    }
}