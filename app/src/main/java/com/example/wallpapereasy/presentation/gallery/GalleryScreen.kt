package com.example.wallpapereasy.presentation.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wallpapereasy.R
import com.example.wallpapereasy.data.api.Hit
import com.example.wallpapereasy.presentation.defaults.ErrorScreen
import com.example.wallpapereasy.presentation.defaults.ProgressBarLoading
import com.example.wallpapereasy.presentation.defaults.WPETopAppBar
import com.example.wallpapereasy.utils.PreviewColors
import com.example.wallpapereasy.utils.WallpaperCategory
import com.example.wallpapereasy.utils.topAppBarHeight
import com.example.wallpapereasy.viewmodel.GalleryViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    category: String,
    onWallpaperClick: (String) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel(),
    modifier: Modifier,
    upPress: () -> Unit
) {
    val wallpaperCategory = WallpaperCategory.valueOf(category)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    //Calculate item height depending on screen parameters, to make item aspect ratio correct
    val itemHeight = screenWidth * 0.5f

    val scrollState = rememberLazyGridState()
    val wallpapers = viewModel.wallpapers.collectAsLazyPagingItems()
    val progressVisible = rememberSaveable { mutableStateOf(true) }
    val errorVisible = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (wallpapers.loadState.refresh is LoadState.Error) {
        if (wallpapers.itemCount == 0) {
            progressVisible.value = false
            errorVisible.value = true
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {

        //Scaffold to handle snack-bar events (add later maybe) and get padding values
        Scaffold { paddingValues ->

            ProgressBarLoading(modifier = modifier, visible = progressVisible.value)
            ErrorScreen(
                modifier = modifier,
                onErrorClick = {
                    wallpapers.refresh()
                    errorVisible.value = false
                    progressVisible.value = true
                },
                visible = errorVisible.value
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp,
                    top = paddingValues.calculateTopPadding() + topAppBarHeight + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = scrollState
            ) {
                items(wallpapers.itemCount) { index ->
                    wallpapers[index]?.let { hit ->
                        WallpaperItem(
                            itemHeight = itemHeight,
                            onWallpaperClick = onWallpaperClick,
                            modifier = modifier,
                            wallpaper = hit,
                            enabled = true
                        )
                    }
                }

                if (wallpapers.loadState.append is LoadState.Loading) {
                    for (i in 0..2) {
                        item {
                            WallpaperItemLoading(
                                itemHeight = itemHeight,
                                modifier = modifier
                            )
                        }
                    }
                }

                if (wallpapers.loadState.append is LoadState.NotLoading || wallpapers.loadState.append is LoadState.Loading) {
                    if (wallpapers.itemCount > 0) {
                        progressVisible.value = false
                    }
                }

                if(wallpapers.loadState.append is LoadState.Error || wallpapers.loadState.refresh is LoadState.Error) {

                    if (wallpapers.itemCount > 0) {
                        coroutineScope.launch {
                            scrollState.scrollToItem(wallpapers.itemCount)
                        }
                        item (span = {GridItemSpan(3)}) {
                            WallpaperItemError(modifier = modifier, onErrorClick = {
                                wallpapers.refresh()
                            })
                        }
                    }
                }
            }
            //TopAppBar overlapped on content to translucent effect
            WPETopAppBar(
                navIconVisible = true,
                wallpaperCategory = wallpaperCategory,
                upPress = upPress
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperItem(
    itemHeight: Dp,
    onWallpaperClick: (String) -> Unit,
    modifier: Modifier,
    wallpaper: Hit,
    enabled: Boolean
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.size(itemHeight),
        onClick = {
            onWallpaperClick(wallpaper.largeImageURL)
        },
        enabled = enabled
    ) {

        val placeHolderVisible = rememberSaveable {
            mutableStateOf(true)
        }

        Box(
            modifier = modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(wallpaper.webformatURL)
                    .crossfade(true)
                    .build(),
                contentDescription = wallpaper.id.toString(),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = placeHolderVisible.value,
                        color = PreviewColors
                            .values()
                            .random().color,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                onSuccess = { placeHolderVisible.value = false }
            )
        }
    }
}

@Composable
fun WallpaperItemLoading(
    itemHeight: Dp,
    modifier: Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.size(itemHeight),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .placeholder(
                    visible = true,
                    color = PreviewColors
                        .values()
                        .random().color,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperItemError(
    modifier: Modifier,
    onErrorClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = { onErrorClick() }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.errorContainer),
            contentAlignment = Alignment.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Error",
                    alignment = Alignment.Center,
                    modifier = modifier
                        .size(100.dp)
                        .padding(4.dp)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.error_item_first_line))
                    Text(stringResource(R.string.error_item_second_line))
                }
            }
        }
    }
}


