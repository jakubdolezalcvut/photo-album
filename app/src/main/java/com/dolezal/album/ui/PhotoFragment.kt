package com.dolezal.album.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.Slide
import com.dolezal.album.R
import com.dolezal.album.data.Album
import com.dolezal.album.vm.PhotoViewModel
import com.google.android.flexbox.*
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
    ): View {
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
        photoToolbar.setupForNavigation {
            fragmentManager?.popBackStackImmediate()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoAdapter = PhotoAdapter()

        photoRefreshLayout.setOnRefreshListener {
            load(force = true)
        }
        photoRecyclerView.apply {
            adapter = photoAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                alignItems = AlignItems.FLEX_START
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.SPACE_AROUND
            }
        }
        photoFloatingActionButton.setOnClickListener { _ ->
            requireFragmentManager().beginTransaction()
                .add(R.id.photoFragmentFrame, UploadFragment(), UploadFragment.TAG)
                .addToBackStack(null)
                .commit()
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
        load(force = false)
    }

    private fun load(force: Boolean) {
        album?.let { album ->
            photoViewModel.load(force, album.id)
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