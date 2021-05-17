package com.example.atgandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.atgandroid.model.FlickrImage
import com.example.atgandroid.model.Photo
import com.example.atgandroid.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    private val imagesData: Flow<PagingData<Photo>> = repository.getImages().cachedIn(viewModelScope)
    var searched: MutableLiveData<Response<FlickrImage>> = MutableLiveData()

    fun getModelImages(): Flow<PagingData<Photo>> {
        return imagesData
    }

    fun getSearchedImages(method: String, api_key: String, format: String, nojsoncallback: Int, extras: String, text: String) {
        viewModelScope.launch {
            val response: Response<FlickrImage> = repository.getSearch(method, api_key, format, nojsoncallback, extras, text)
            searched.value = response
        }
    }
}