package com.example.atgandroid.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.atgandroid.MainViewModel
import com.example.atgandroid.adapter.ImageAdapter
import com.example.atgandroid.databinding.FragmentGalleryBinding
import com.example.atgandroid.model.ImageGridModel
import com.example.atgandroid.repository.MainViewModelFactory
import com.example.atgandroid.repository.Repository

class GalleryFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)

        val gridLayoutManager: GridLayoutManager?
        val imageList: ArrayList<ImageGridModel> = ArrayList()
        val imageAdapter: ImageAdapter = ImageAdapter(context, imageList)

        gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.imageRecyclerView.layoutManager = gridLayoutManager
        binding.imageRecyclerView.setHasFixedSize(true)
        binding.imageRecyclerView.adapter =imageAdapter


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getFlickrImages("flickr.photos.getRecent", 20, 1, "6f102c62f41998d151e5a1b48713cf13", "json", 1, "url_s")
        viewModel.images.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {

                response.body()?.getPhotoModel()?.getPhotoList()?.forEach {
                    imageList.add(ImageGridModel(it.url_s.toString(), it.title.toString()))
                }

                imageAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}
