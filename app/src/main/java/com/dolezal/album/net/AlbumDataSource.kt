package com.dolezal.album.net

import com.dolezal.album.data.Album
import io.reactivex.Single

interface AlbumDataSource {
    fun getAlbums(): Single<List<Album>>
}