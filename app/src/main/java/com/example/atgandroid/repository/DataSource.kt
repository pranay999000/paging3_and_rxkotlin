package com.example.atgandroid.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.atgandroid.api.FlickrApi
import com.example.atgandroid.model.Photo
import retrofit2.HttpException
import java.io.IOException

class DataSource : PagingSource<Int, Photo>() {
    private val flickrApi = FlickrApi()

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {

            val nextPageNumber = params.key ?: 1
            val response = flickrApi.getImages(
                    "flickr.photos.getRecent",
                    20,
                    nextPageNumber,
                    "6f102c62f41998d151e5a1b48713cf13",
                    "json",
                    1,
                    "url_s"
            )
            val data = response.photos.photo
            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (data.isEmpty()) null else nextPageNumber + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}
