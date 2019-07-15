package com.dolezal.album.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dolezal.album.R
import com.dolezal.album.data.Photo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_photo.*

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    var photos = emptyList<Photo>()
        set(value) {
            field = value.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.viewholder_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class PhotoViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(photo: Photo) {
            photoTitle.text = photo.title

            Glide.with(containerView)
                .load(photo.thumbnailUrl)
                .error(R.drawable.skull_crossbones_outline)
                .placeholder(R.drawable.loading_wheel)
                .centerCrop()
                .into(photoImage)
        }
    }
}