package com.devcomentry.photogallery.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.devcomentry.photogallery.ui.theme.Typography

@Composable
fun GalleryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}