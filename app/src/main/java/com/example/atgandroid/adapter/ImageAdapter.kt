package com.example.atgandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.atgandroid.databinding.ImageItemBinding
import com.example.atgandroid.fragment.GalleryFragmentDirections
import com.example.atgandroid.model.ImageGridModel

class ImageAdapter(private var context: Context?, private var list: ArrayList<ImageGridModel>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with (holder) {
            with (list[position]) {
                Glide.with(context!!).load(image).into(binding.imageView)
                binding.imageView.setOnClickListener { view: View ->
                    view.findNavController().navigate(GalleryFragmentDirections.actionGalleryFragmentToImageFragment(image, title))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root)
}