package com.dolezal.album.ui

import android.content.res.Resources
import com.dolezal.album.R
import com.dolezal.album.data.Album

class UploadValidator(
    private val resources: Resources
) {
    fun validate(
        title: String,
        album: Album?,
        onTitleError: (String) -> Unit,
        onAlbumError: (String) -> Unit,
        onSuccess: (String, Album) -> Unit
    ) {
        val isTitleValid = title.isNotBlank()
        val isAlbumValid = album != null && album != Album.NONE

        if (!isTitleValid) {
            val error = resources.getString(R.string.upload_validation_error_title)
            onTitleError(error)
        }
        if (!isAlbumValid) {
            val error = resources.getString(R.string.upload_validation_error_album)
            onAlbumError(error)
        }
        if (isTitleValid && isAlbumValid) {
            onSuccess(title, album!!)
        }
    }
}