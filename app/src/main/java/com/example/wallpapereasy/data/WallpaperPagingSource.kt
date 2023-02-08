package com.example.wallpapereasy.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpapereasy.data.api.Hit
import com.example.wallpapereasy.data.api.ImageApi
import kotlinx.coroutines.delay

class WallpaperPagingSource(
    private val imageApi: ImageApi, private val category: String
) : PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        return try {
            val response = imageApi.getImages(category = category, page = page, pageSize = pageSize)
            return LoadResult.Page(
                data = response.hits,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.hits.size < pageSize) null else page + 1
            )

        } catch (e: Exception) {
            delay(500)
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return null
    }


}