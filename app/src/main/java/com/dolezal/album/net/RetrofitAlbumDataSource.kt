package com.dolezal.album.net

import com.dolezal.album.data.Album
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitAlbumDataSource(
    private val itemService: AlbumService
) : AlbumDataSource {

    override fun getAlbums(): Single<List<Album>> {
        return itemService.getAlbums()
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