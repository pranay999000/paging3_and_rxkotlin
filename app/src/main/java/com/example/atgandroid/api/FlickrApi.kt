package com.example.atgandroid.api

import com.example.atgandroid.model.Images
import com.example.atgandroid.model.PhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("rest")
    suspend fun getImages(
        @Query("method") method: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String,
        @Query("format") format:String,
        @Query("nojsoncallback") nojsoncallback: Int,
        @Query("extras") extras:String
    ): Response<PhotoModel>
}