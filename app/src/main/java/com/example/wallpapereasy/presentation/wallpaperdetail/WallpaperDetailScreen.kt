package com.example.wallpapereasy.presentation.wallpaperdetail

import android.app.WallpaperManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.*
import coil.request.ImageRequest
import com.example.wallpapereasy.presentation.defaults.ErrorScreen
import com.example.wallpapereasy.presentation.defaults.ProgressBarLoading
import com.example.wallpapereasy.presentation.defaults.WPETopAppBar
import com.example.wallpapereasy.utils.topAppBarHeight
import com.example.wallpapereasy.viewmodel.ImageResult
import com.example.wallpapereasy.viewmodel.WallpaperViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperDetailScreen(
    wallpaperUrl: String,
    modifier: Modifier,
    upPress: () -> Unit,
    viewModel: WallpaperViewModel = hiltViewModel()
) {

    val wallpaperManager = WallpaperManager.getInstance(LocalContext.current)
    val imageResult = viewModel.imageFlow.collectAsState()

    val request = ImageRequest.Builder(LocalContext.current)
        .data(wallpaperUrl)
        .build()
    viewModel.loadImage(request)

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->

            when (imageResult.value) {
                is ImageResult.Loading -> {
                    ProgressBarLoading(modifier = modifier, visible = true)
                }
                is ImageResult.Exception -> {
                    ErrorScreen(
                        modifier = modifier,
                        onErrorClick = { viewModel.loadImage(request) },
                        visible = true
                    )
                }
                is ImageResult.Success -> {
                    WallpaperItem(
                        modifier = modifier,
                        paddingValues = paddingValues,
                        image = (imageResult.value as ImageResult.Success).data,
                        onWallpaperApply = {
                            wallpaperManager.setBitmap((imageResult.value as ImageResult.Success).data.toBitmap())
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Wallpaper applied!")
                            }
                        }
                    )
                    WPETopAppBar(
                        navIconVisible = true,
                        upPress = upPress
                    )

                }
            }
        }
    }
}

@Composable
fun WallpaperItem(
    modifier: Modifier,
    paddingValues: PaddingValues,
    image: Drawable,
    onWallpaperApply: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize().padding(
                top = paddingValues.calculateTopPadding() + topAppBarHeight,
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            )
    ) {

        AsyncImage(
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = modifier.fillMaxSize()
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            ),
        contentAlignment = Alignment.BottomCenter
    )
    {
        FilledTonalButton(
            onClick = {
                onWallpaperApply()
            },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            Icon(
                Icons.Filled.Check,
                contentDescription = "Refresh",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Set wallpaper")
        }

    }

}
