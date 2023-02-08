package com.example.wallpapereasy.data.api

import com.google.gson.annotations.SerializedName


data class ImageApiResponse(

    @SerializedName("total")
    var total: Int,

    @SerializedName("totalHits")
    var totalHits: Int,

    @SerializedName("hits")
    var hits: List<Hit>
)