package com.dolezal.album.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolezal.album.R
import com.dolezal.album.data.Album
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_album.*

class AlbumAdapter(
    private val onClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var albums = emptyList<Album>()
        set(value) {
            field = value.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.viewholder_album, parent, false)
        return AlbumViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(
        override val containerView: View,
        private val onClick: (Album) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(album: Album) {
            albumTitle.text = album.title
            containerView.setOnClickListener { _ -> onClick(album) }
        }
    }
}