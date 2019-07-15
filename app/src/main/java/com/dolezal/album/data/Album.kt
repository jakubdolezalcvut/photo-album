package com.dolezal.album.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AlbumDTO(
    @SerializedName("userId") val userId: Long,
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String
) {
    fun toDomain(): Album {
        return Album(
            userId = userId,
            id = id,
            title = title
        )
    }
}

@Parcelize
data class Album(
    val userId: Long,
    val id: Long,
    val title: String
) : Parcelable