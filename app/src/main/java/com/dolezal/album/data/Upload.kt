package com.dolezal.album.data

import com.google.gson.annotations.SerializedName

data class UploadDTO(
    @SerializedName("id") val id: Long
) {
    fun toDomain(): Upload {
        return Upload(id)
    }
}

data class Upload(val id: Long)