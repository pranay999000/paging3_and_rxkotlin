package com.example.atgandroid.repository

import com.example.atgandroid.api.RetrofitInstance
import com.example.atgandroid.model.Images
import com.example.atgandroid.model.PhotoModel
import retrofit2.Response

class Repository {
    suspend fun getImages(method: String, per_page: Int, page: Int, api_key: String, format: String, nojsoncallback: Int, extras: String): Response<PhotoModel> {
        return RetrofitInstance.api.getImages(method, per_page, page, api_key, format, nojsoncallback, extras)
    }
}