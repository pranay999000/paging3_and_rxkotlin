package com.example.atgandroid.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atgandroid.MainViewModel
import com.example.atgandroid.R
import com.example.atgandroid.adapter.SearchedAdapter
import com.example.atgandroid.databinding.FragmentSearchBinding
import com.example.atgandroid.model.Photo
import com.example.atgandroid.repository.MainViewModelFactory
import com.example.atgandroid.repository.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val imageList: ArrayList<Photo> = ArrayList()
        val searchedAdapter: SearchedAdapter = SearchedAdapter(context, imageList)

        val gridLayoutManager: GridLayoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.searchedRecyclerView.layoutManager = gridLayoutManager
        binding.searchedRecyclerView.setHasFixedSize(true)
        binding.searchedRecyclerView.adapter = searchedAdapter

        binding.searchedRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val inputMethod = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethod.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }
        })

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.searched.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            imageList.clear()
            if (response.isSuccessful) {
                response.body()?.photos?.photo?.forEach {
                    imageList.add(Photo(it.id.toString(), it.title.toString(), it.url_s.toString()))
                }
                searchedAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun search(t: String) {
//        Toast.makeText(context, t, Toast.LENGTH_SHORT).show()
//        Log.d("Observer", t)
        viewModel.getSearchedImages(
                "flickr.photos.search",
                "6f102c62f41998d151e5a1b48713cf13",
                "json",
                1,
                "url_s",
                t
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_view, menu)

        val menuItem = menu.findItem(R.id.search_bar)
        val searchView = menuItem.actionView as SearchView
        searchView.isIconified = false

        val observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>?) {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!emitter?.isDisposed!!) {
                            emitter.onNext(newText)
                        }
                        return false
                    }

                })
            }

        }).debounce(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())

        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                disposables.add(d)
            }

            override fun onNext(t: String?) {
                if (t != null) {
                    search(t)
                }
            }

            override fun onError(e: Throwable?) {
                //TODO("Not yet implemented")
            }

            override fun onComplete() {
                //TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
