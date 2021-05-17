package com.example.atgandroid.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atgandroid.MainViewModel
import com.example.atgandroid.R
import com.example.atgandroid.adapter.Adapter
import com.example.atgandroid.adapter.ImageAdapter
import com.example.atgandroid.databinding.FragmentGalleryBinding
import com.example.atgandroid.model.Photo
import com.example.atgandroid.repository.MainViewModelFactory
import com.example.atgandroid.repository.Repository
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

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

        setHasOptionsMenu(true)

        val gridLayoutManager: GridLayoutManager?
        val imageList: ArrayList<Photo> = ArrayList()
        val imageAdapter = ImageAdapter()

        gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.imageRecyclerView.layoutManager = gridLayoutManager
        binding.imageRecyclerView.setHasFixedSize(true)
        binding.imageRecyclerView.adapter = imageAdapter.withLoadStateHeaderAndFooter(
                header = Adapter { imageAdapter.retry() },
                footer = Adapter { imageAdapter.retry() }
        )

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel  = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getModelImages()
        lifecycleScope.launch {
            viewModel.getModelImages().collectLatest { pageData ->
                imageAdapter.submitData(pageData)
            }
        }

//        lifecycleScope.launch {
//            imageAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
//                    .filter { it.refresh is LoadState.NotLoading }
//                    .collect { binding.imageRecyclerView.scrollToPosition(0) }
//        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view?.findNavController()!!) || super.onOptionsItemSelected(item)
    }
}
