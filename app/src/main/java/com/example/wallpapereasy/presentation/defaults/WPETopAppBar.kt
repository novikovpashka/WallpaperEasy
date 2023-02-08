package com.example.wallpapereasy.presentation.defaults

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.wallpapereasy.R
import com.example.wallpapereasy.ui.theme.lobsterFamily
import com.example.wallpapereasy.ui.theme.md_theme_dark_primary
import com.example.wallpapereasy.ui.theme.md_theme_dark_secondary
import com.example.wallpapereasy.utils.WallpaperCategory


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun WPETopAppBar(
    navIconVisible: Boolean,
    wallpaperCategory: WallpaperCategory? = null,
    upPress: () -> Unit
) {

    val gradientColors = listOf(md_theme_dark_primary, md_theme_dark_secondary)

    val colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent, scrolledContainerColor = Color.Transparent
    )

    Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.alpha(0.95f)) {
        CenterAlignedTopAppBar(
            title = {
                if (wallpaperCategory == null) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = lobsterFamily,
                        style = TextStyle(brush = Brush.linearGradient(gradientColors)),
                        fontSize = 24.sp
                    )
                } else {
                    Text(
                        wallpaperCategory.categoryName,
                        fontFamily = lobsterFamily,
                        style = TextStyle(brush = Brush.linearGradient(gradientColors)),
                        fontSize = 24.sp
                    )
                }
            },
            colors = colors,
            navigationIcon = {
                if (navIconVisible)
                    IconButton(
                        onClick = { upPress() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
            }
        )
    }
}
