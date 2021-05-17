package com.example.atgandroid.api

import com.example.atgandroid.api.Links.Companion.FLICKR_URL
import com.example.atgandroid.model.FlickrImage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    companion object {
        operator fun invoke(): FlickrApi = Retrofit.Builder()
                .baseUrl(FLICKR_URL)
                .client(OkHttpClient.Builder().also { client ->
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BASIC
                    client.addInterceptor(logging)
                }.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrApi::class.java)
    }

    @GET("rest")
    suspend fun getImages(
        @Query("method") method: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String,
        @Query("format") format:String,
        @Query("nojsoncallback") nojsoncallback: Int,
        @Query("extras") extras:String
    ): FlickrImage

    @GET("rest")
    suspend fun getSearch(
        @Query("method") method:String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int,
        @Query("extras") extras: String,
        @Query("text") text: String
    ): Response<FlickrImage>
}