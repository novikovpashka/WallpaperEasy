package com.example.wallpapereasy.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.wallpapereasy.data.api.ImageApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WallpaperRepository @Inject constructor(private val imageApi: ImageApi) {

    @OptIn(FlowPreview::class)
    fun getWallpapers(category: String) = Pager(
        PagingConfig(pageSize = 15)
    ) {
        WallpaperPagingSource(imageApi = imageApi, category = category)
    }.flow.debounce(200)
}