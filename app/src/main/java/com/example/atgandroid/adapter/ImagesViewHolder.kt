package com.example.atgandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.atgandroid.R
import com.example.atgandroid.fragment.GalleryFragmentDirections
import com.example.atgandroid.model.Photo
import kotlinx.android.synthetic.main.image_item.view.*

class ImagesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(images: Photo?) {
        if (images != null) {
            Glide.with(itemView).load(images.url_s).into(itemView.image_view)
            itemView.setOnClickListener { view: View ->
                view.findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToImageFragment(
                    images.url_s.toString(), images.title.toString()
                ))
            }
        }
    }

    companion object {
        fun create (parent: ViewGroup): ImagesViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
            return ImagesViewHolder(view)
        }
    }
}