package com.dolezal.album.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.Slide
import com.dolezal.album.R
import com.dolezal.album.data.Album
import com.dolezal.album.vm.AlbumViewModel
import com.dolezal.album.vm.UploadViewModel
import kotlinx.android.synthetic.main.fragment_upload.*

class UploadFragment : Fragment() {

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var uploadValidator: UploadValidator
    private lateinit var uploadStateRenderer: UploadStateRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.LEFT)
        exitTransition = Slide(Gravity.LEFT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadValidator = UploadValidator(resources)
        uploadStateRenderer = UploadStateRenderer(uploadBtn) {
            fragmentManager?.popBackStackImmediate()
        }
        uploadToolbar.setupForNavigation {
            fragmentManager?.popBackStackImmediate()
        }
        uploadBtn.setOnClickListener { _ ->
            uploadValidator.validate(
                title = uploadTitleEditText.text.toString(),
                album = uploadAlbumSpinner.selectedItem as? Album,
                onTitleError = { error ->
                    uploadTitleEditText.error = error
                },
                onAlbumError = { error ->
                    val errorView = uploadAlbumSpinner.selectedView as TextView
                    errorView.setError(error)
                },
                onSuccess = { title, album ->
                    uploadViewModel.upload(title = title, albumId = album.id)
                })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        albumViewModel = getViewModel { scope ->
            AlbumViewModel.create(scope)
        }.apply {
            albums.observe(viewLifecycleOwner, Observer { albums ->
                val allAlbums = listOf(Album.NONE) + albums
                uploadAlbumSpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, allAlbums)
            })
            load(force = false)
        }

        uploadViewModel = getViewModel { scope ->
            UploadViewModel.create(scope)
        }.apply {
            networkState.observe(viewLifecycleOwner, Observer { state ->
                uploadStateRenderer.render(state)
            })
        }
    }

    companion object {
        const val TAG = "upload-fragment"
    }
}