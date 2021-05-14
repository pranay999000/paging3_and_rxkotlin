package com.example.atgandroid.api

import com.example.atgandroid.BuildConfig
import com.example.atgandroid.api.Links.Companion.FLICKR_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(FLICKR_URL).addConverterFactory(GsonConverterFactory.create()).client(getLogInterceptor()).build()
    }

    val api: FlickrApi by lazy {
        retrofit.create(FlickrApi::class.java)
    }

    private fun getLogInterceptor(): OkHttpClient {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            clientBuilder.addInterceptor(interceptor)
        }
        return clientBuilder.build()
    }
}