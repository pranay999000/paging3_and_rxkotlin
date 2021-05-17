package com.example.atgandroid.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.atgandroid.api.FlickrApi
import com.example.atgandroid.model.FlickrImage
import com.example.atgandroid.model.Photo
import com.example.atgandroid.model.Photos
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository {
    private val flickrApi = FlickrApi()

    fun getImages (): Flow<PagingData<Photo>> {
        return Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = true),
                pagingSourceFactory = { DataSource() }
        ).flow
    }

    suspend fun getSearch(method: String, api_key: String, format: String, nojsoncallback: Int, extras: String, text: String):
            Response<FlickrImage> {
        return flickrApi.getSearch(method, api_key, format, nojsoncallback, extras, text)
    }
}