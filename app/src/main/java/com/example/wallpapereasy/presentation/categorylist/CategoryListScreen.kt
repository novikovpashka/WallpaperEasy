package com.example.wallpapereasy.presentation.categorylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpapereasy.presentation.defaults.WPETopAppBar
import com.example.wallpapereasy.ui.theme.lobsterFamily
import com.example.wallpapereasy.utils.WallpaperCategory
import com.example.wallpapereasy.utils.topAppBarHeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    modifier: Modifier, onCategoryClick: (String) -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    //Calculate item height depending on screen parameters, to make item aspect ratio correct
    val itemHeight = screenWidth * 0.6f

    val scrollState = rememberLazyGridState()

        Surface(modifier = modifier.fillMaxSize()) {
            //Scaffold to handle snack-bar events (add later maybe) and get padding values
            Scaffold { paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = paddingValues.calculateBottomPadding() + 8.dp,
                        top = paddingValues.calculateTopPadding() + topAppBarHeight + 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    state = scrollState,
                ) {
                    items(WallpaperCategory.values()) { category ->
                        CategoryItem(
                            itemHeight = itemHeight,
                            onCategoryClick = onCategoryClick,
                            modifier = modifier,
                            category = category,
                        )
                    }
                }
                //TopAppBar overlapped on content to translucent effect
                WPETopAppBar(navIconVisible = false, upPress = {})
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    itemHeight: Dp,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier,
    category: WallpaperCategory,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.size(itemHeight),
        onClick = {
            onCategoryClick(category.name)
        }
    ) {

        Box(
            modifier = modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = category.previewPicture),
                contentDescription = category.parameter,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxSize()
            )

            //Translucent cover for image to font readability
            Surface(
                modifier = modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.3f)
            ) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.categoryName,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = lobsterFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                }
            }
        }
    }
}
