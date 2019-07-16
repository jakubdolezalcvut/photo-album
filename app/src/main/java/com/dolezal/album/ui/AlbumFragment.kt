package com.dolezal.album.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolezal.album.R
import com.dolezal.album.vm.AlbumViewModel
import kotlinx.android.synthetic.main.fragment_album.*

class AlbumFragment : Fragment() {

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var networkStateRenderer: NetworkStateRenderer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkStateRenderer = NetworkStateRenderer(
            containerView = view,
            refreshLayout = albumRefreshLayout,
            progressBar = albumProgressBar,
            emptyMessage = albumEmptyMessage
        )
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            albumToolbar.visibility = View.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val albumAdapter = getAdapter()

        albumRefreshLayout.setOnRefreshListener {
            albumViewModel.load(force = true)
        }
        albumRecyclerView.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(context)
        }

        albumViewModel = getViewModel { scope ->
            AlbumViewModel.create(scope)
        }.apply {
            albums.observe(viewLifecycleOwner, Observer { albums ->
                albumAdapter.albums = albums
            })
            networkState.observe(viewLifecycleOwner, Observer { state ->
                networkStateRenderer.render(state)
            })
            load(force = false)
        }
    }

    private fun getAdapter(): AlbumAdapter {
        return AlbumAdapter { album ->
            val fragment = PhotoFragment.getInstance(album)

            requireFragmentManager().apply {
                popBackStack()
                beginTransaction()
                    .replace(R.id.photoFrame, fragment, PhotoFragment.TAG)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}