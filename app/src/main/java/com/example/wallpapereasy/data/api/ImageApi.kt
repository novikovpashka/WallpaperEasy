package com.example.wallpapereasy.data.api

import com.example.wallpapereasy.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET(".")
    suspend fun getImages(
        @Query("key") key: String = API_KEY,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("min_width") minWidth: Int = 720,
        @Query("min_height") minHeight: Int = 1280,
        @Query("orientation") orientation: String = "vertical"
    ): ImageApiResponse

}