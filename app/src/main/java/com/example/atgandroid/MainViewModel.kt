package com.example.atgandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atgandroid.model.Images
import com.example.atgandroid.model.PhotoModel
import com.example.atgandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    var images: MutableLiveData<Response<PhotoModel>> = MutableLiveData()

    fun getFlickrImages(method: String, per_page: Int, page: Int, api_key: String, format: String, nojsoncallback: Int, extras: String) {
        viewModelScope.launch {
            val response:Response<PhotoModel> = repository.getImages(method, per_page, page, api_key, format, nojsoncallback, extras)
            images.value = response
        }
    }
}