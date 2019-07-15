package com.dolezal.album.net

import com.dolezal.album.data.AlbumDTO
import com.dolezal.album.data.PhotoDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {

    @GET("albums")
    fun getAlbums(): Single<List<AlbumDTO>>

    @GET("photos")
    fun getPhotos(@Query("albumId") albumId: Long): Single<List<PhotoDTO>>
}