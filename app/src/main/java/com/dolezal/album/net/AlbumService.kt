package com.dolezal.album.net

import com.dolezal.album.data.AlbumDTO
import com.dolezal.album.data.PhotoDTO
import com.dolezal.album.data.UploadDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AlbumService {

    @GET("albums")
    fun getAlbums(): Single<List<AlbumDTO>>

    @GET("photos")
    fun getPhotos(@Query("albumId") albumId: Long): Single<List<PhotoDTO>>

    @POST("photos")
    fun uploadPhoto(
        @Query("title") title: String,
        @Query("albumId") albumId: Long
    ): Single<UploadDTO>
}