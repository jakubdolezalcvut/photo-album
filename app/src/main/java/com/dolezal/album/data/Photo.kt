package com.dolezal.album.data

import com.google.gson.annotations.SerializedName

data class PhotoDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String
) {
    fun toDomain(): Photo {
        return Photo(
            id = id,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }
}

data class Photo(
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)