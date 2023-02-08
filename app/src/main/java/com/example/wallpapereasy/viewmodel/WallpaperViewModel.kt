package com.example.wallpapereasy.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WallpaperViewModel @Inject constructor(private val imageLoader: ImageLoader) : ViewModel() {

    val imageFlow = MutableStateFlow<ImageResult>(ImageResult.Loading)

    fun loadImage(request: ImageRequest) = viewModelScope.launch {
        imageFlow.value = ImageResult.Loading
        try {
            val drawable = imageLoader.execute(request).drawable
            imageFlow.value = ImageResult.Success(drawable!!)
        } catch (e: Exception) {
            imageFlow.value = ImageResult.Exception(e)
        }
    }
}

sealed class ImageResult {
    class Success(val data: Drawable) : ImageResult()
    object Loading : ImageResult()
    class Exception(val e: Throwable) : ImageResult()
}



