package com.example.wallpapereasy

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.wallpapereasy.extensions.encodeAsUrl
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


object Destinations {
    const val SPLASH_ROUTE = "splash"
    const val CATEGORY_ROUTE = "category"
    const val WALLPAPER_GALLERY_ROUTE = "wallpaperGallery"
    const val WALLPAPER_DETAIL_ROUTE = "wallpaperDetail"
    const val CATEGORY_NAME_KEY = "categoryName"
    const val WALLPAPER_ID_KEY = "wallpaperKey"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    navController: NavHostController = rememberAnimatedNavController()
) = remember(navController) {
    AppState(navController)
}

@Stable
class AppState(
    val navController: NavHostController,
) {
    fun navigateToCategory(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(Destinations.CATEGORY_ROUTE) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    fun navigateToWallpaperGallery(category: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${Destinations.WALLPAPER_GALLERY_ROUTE}/$category")
        }
    }

    fun navigateToWallpaperDetail(wallpaperUrl: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${Destinations.WALLPAPER_DETAIL_ROUTE}/${wallpaperUrl.encodeAsUrl()}")
        }
    }

    fun upPress() {
        navController.navigateUp()
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED


