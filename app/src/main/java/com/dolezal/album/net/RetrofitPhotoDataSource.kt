package com.dolezal.album.net

import com.dolezal.album.data.Photo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitPhotoDataSource(
    private val itemService: AlbumService
) : PhotoDataSource {

    override fun getPhotos(albumId: Long): Single<List<Photo>> {
        return itemService.getPhotos(albumId)
            .subscribeOn(Schedulers.io())
            .retry(RETRY_COUNT)
            .map { dtoList ->
                dtoList.map { dto -> dto.toDomain() }
            }
    }

    companion object {
        private const val RETRY_COUNT = 2L
    }
}