package com.dolezal.album.net

import com.dolezal.album.data.Photo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitPhotoDataSource(
    private val albumService: AlbumService
) : PhotoDataSource {

    override fun getPhotos(albumId: Long): Single<List<Photo>> {
        return albumService.getPhotos(albumId)
            .subscribeOn(Schedulers.io())
            .retry(RETRY_COUNT)
            .map { dtoList ->
                dtoList.map { dto -> dto.toDomain() }
            }
    }

    override fun uploadPhoto(title: String, albumId: Long): Single<String> {
        return albumService.uploadPhoto(title, albumId)
            .subscribeOn(Schedulers.io())
            .retry(RETRY_COUNT)
    }

    companion object {
        private const val RETRY_COUNT = 2L
    }
}