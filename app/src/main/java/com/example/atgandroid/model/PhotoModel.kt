package com.example.atgandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhotoModel {

    @SerializedName("photos")
    @Expose
    private var photoModel: PhotoModel? = null

    @SerializedName("photo")
    private var photoList: List<Images>? = null

    fun getPhotoModel(): PhotoModel? {
        return photoModel
    }

    fun getPhotoList(): List<Images>? {
        return photoList
    }
}