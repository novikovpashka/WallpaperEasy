package com.example.wallpapereasy.presentation.defaults

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wallpapereasy.R

@Composable
fun ErrorScreen(modifier: Modifier, onErrorClick: () -> Unit, visible: Boolean) {
    if (visible) {

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Error",
                    alignment = Alignment.Center,
                    modifier = modifier.size(150.dp)
                )
                Text(text = stringResource(R.string.error_first_line))
                Text(text = stringResource(R.string.error_second_line))
                FilledTonalButton(
                    onClick = { onErrorClick() },
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    )
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Refresh",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.icon_refresh_after_error))
                }
            }
        }
    }
}