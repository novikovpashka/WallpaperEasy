package com.example.wallpapereasy.utils

import com.example.wallpapereasy.R


enum class WallpaperCategory (val categoryName: String, val parameter: String, val previewPicture: Int) {
    NATURE (categoryName = "Nature", parameter = "nature", previewPicture = R.drawable.preview_nature),
    EDUCATION (categoryName = "Education", parameter = "education", previewPicture = R.drawable.preview_education),
    RELIGION (categoryName = "Religion", parameter = "religion", previewPicture = R.drawable.preview_religion),
    PLACES (categoryName = "Places", parameter = "places", previewPicture = R.drawable.preview_places),
    ANIMALS (categoryName = "Animals", parameter = "animals", previewPicture = R.drawable.preview_animals),
    SPORTS (categoryName = "Sports", parameter = "sports", previewPicture = R.drawable.preview_sports),
    TRAVEL (categoryName = "Travel", parameter = "travel", previewPicture = R.drawable.preview_travel),
    FOOD (categoryName = "Food", parameter = "food", previewPicture = R.drawable.preview_food)
}