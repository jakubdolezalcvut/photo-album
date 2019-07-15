package com.dolezal.album.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.dolezal.album.R
import com.dolezal.album.data.Album
import com.dolezal.album.vm.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    private lateinit var photoViewModel: PhotoViewModel
    private lateinit var networkStateRenderer: NetworkStateRenderer
    private var album: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.LEFT)
        exitTransition = Slide(Gravity.LEFT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments ?: Bundle.EMPTY

        if (bundle.containsKey(ALBUM_KEY)) {
            album = bundle[ALBUM_KEY] as Album
        }
        networkStateRenderer = NetworkStateRenderer(
            containerView = view,
            refreshLayout = photoRefreshLayout,
            progressBar = photoProgressBar,
            emptyMessage = photoEmptyMessage
        )
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            photoToolbar.visibility = View.GONE
        } else {
            photoToolbar.setNavigationOnClickListener { _ ->
                fragmentManager?.popBackStackImmediate()
            }
            photoToolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoAdapter = PhotoAdapter()

        photoRefreshLayout.setOnRefreshListener {
            load()
        }
        photoRecyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        photoViewModel = getViewModel { scope ->
            PhotoViewModel.create(scope)
        }.apply {
            photos.observe(viewLifecycleOwner, Observer { photos ->
                photoAdapter.photos = photos
            })
            networkState.observe(viewLifecycleOwner, Observer { state ->
                networkStateRenderer.render(state)
            })
        }
        load()
    }

    private fun load() {
        album?.let { album ->
            photoViewModel.load(album.id)
        }
    }

    companion object {
        private const val ALBUM_KEY = "album.item"
        const val TAG = "photo-fragment"

        fun getInstance(album: Album): PhotoFragment {
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ALBUM_KEY, album)
                }
            }
        }
    }
}