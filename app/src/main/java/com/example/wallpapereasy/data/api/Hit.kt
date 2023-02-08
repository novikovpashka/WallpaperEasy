package com.example.wallpapereasy.data.api

import com.google.gson.annotations.SerializedName


data class Hit(

    @SerializedName("id")
    val id: Int,

    @SerializedName("pageURL")
    val pageURL: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("tags")
    val tags: String,

    @SerializedName("previewURL")
    val previewURL: String,

    @SerializedName("previewWidth")
    val previewWidth: Int,

    @SerializedName("previewHeight")
    val previewHeight: Int,

    @SerializedName("webformatURL")
    val webformatURL: String,

    @SerializedName("webformatWidth")
    val webformatWidth: Int,

    @SerializedName("webformatHeight")
    val webformatHeight: Int,

    @SerializedName("largeImageURL")
    val largeImageURL: String,

    @SerializedName("fullHDURL")
    val fullHDURL: String,

    @SerializedName("imageURL")
    val imageURL: String,

    @SerializedName("imageWidth")
    val imageWidth: Int,

    @SerializedName("imageHeight")
    val imageHeight: Int,

    @SerializedName("imageSize")
    val imageSize: Int,

    @SerializedName("views")
    val views: Int,

    @SerializedName("downloads")
    val downloads: Int,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("comments")
    val comments: Int,

    @SerializedName("user_id")
    val userId: Int?,

    @SerializedName("user")
    val user: String,

    @SerializedName("userImageURL")
    val userImageURL: String
)