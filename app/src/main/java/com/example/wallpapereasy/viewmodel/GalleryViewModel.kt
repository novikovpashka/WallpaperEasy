package com.example.wallpapereasy.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallpapereasy.Destinations
import com.example.wallpapereasy.data.WallpaperRepository
import com.example.wallpapereasy.utils.WallpaperCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(
    wallpaperRepository: WallpaperRepository,
    state: SavedStateHandle
) : ViewModel() {

    private var category: String? = state[Destinations.CATEGORY_NAME_KEY]
    private val categoryParameter = WallpaperCategory.valueOf(category ?: "").parameter

    val wallpapers = wallpaperRepository.getWallpapers(categoryParameter).cachedIn(viewModelScope)

}



