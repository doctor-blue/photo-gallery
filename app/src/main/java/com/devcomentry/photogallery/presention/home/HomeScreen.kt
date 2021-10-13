package com.devcomentry.photogallery.presention.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.devcomentry.photogallery.presention.utils.PermissionUtils

@Composable
fun HomeScreen(navController: NavController) {

    PermissionUtils.checkPermission(LocalContext.current, {}, {})

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Change the logo
        Text(
            "Hí anh em!!! This is our home screen",
            style = MaterialTheme.typography.h5
        )
    }
}