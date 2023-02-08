package com.example.wallpapereasy

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.example.wallpapereasy.extensions.decodeUrl
import com.example.wallpapereasy.presentation.categorylist.CategoryListScreen
import com.example.wallpapereasy.presentation.gallery.GalleryScreen
import com.example.wallpapereasy.presentation.splash.SplashScreen
import com.example.wallpapereasy.presentation.wallpaperdetail.WallpaperDetailScreen
import com.example.wallpapereasy.ui.theme.WallpaperEasyTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    WallpaperEasyTheme {

        val appState = rememberAppState()
        AnimatedNavHost(
            navController = appState.navController,
            startDestination = Destinations.SPLASH_ROUTE,
        ) {
            wallpaperEasyNavGraph(
                onCategorySelect = appState::navigateToWallpaperGallery,
                onWallpaperSelect = appState::navigateToWallpaperDetail,
                navigateToCategory = appState::navigateToCategory,
                upPress = appState::upPress
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.wallpaperEasyNavGraph(
    onCategorySelect: (String, NavBackStackEntry) -> Unit,
    onWallpaperSelect: (String, NavBackStackEntry) -> Unit,
    navigateToCategory:(NavBackStackEntry) -> Unit,
    upPress:() -> Unit
) {

    composable(route = Destinations.SPLASH_ROUTE) { from ->
        SplashScreen(modifier = Modifier, navigateToCategory = { navigateToCategory(from) })
    }

    composable(route = Destinations.CATEGORY_ROUTE) { from ->
        CategoryListScreen(
            onCategoryClick = { category ->
                onCategorySelect(category, from)
            },
            modifier = Modifier
        )
    }
    composable(
        route = "${Destinations.WALLPAPER_GALLERY_ROUTE}/{${Destinations.CATEGORY_NAME_KEY}}",
        arguments = listOf(navArgument(Destinations.CATEGORY_NAME_KEY) {
            type = NavType.StringType
        })
    ) { from ->
        val arguments = requireNotNull(from.arguments)
        val categoryName = arguments.getString(Destinations.CATEGORY_NAME_KEY)
        GalleryScreen(
            category = categoryName!!,
            onWallpaperClick = { wallpaperUrl ->
                onWallpaperSelect(wallpaperUrl, from)
            },
            upPress = upPress,
            modifier = Modifier
        )
    }
    composable(
        route = "${Destinations.WALLPAPER_DETAIL_ROUTE}/{${Destinations.WALLPAPER_ID_KEY}}",
        arguments = listOf(navArgument(Destinations.WALLPAPER_ID_KEY) {
            type = NavType.StringType
        })
    ) { from ->
        val arguments = requireNotNull(from.arguments)
        val wallpaperUrlEncoded = arguments.getString(Destinations.WALLPAPER_ID_KEY) ?: ""

        WallpaperDetailScreen(
            wallpaperUrl = wallpaperUrlEncoded.decodeUrl(),
            modifier = Modifier,
            upPress = upPress
        )
    }
}