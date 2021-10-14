package com.devcomentry.photogallery.presention.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AllFileScreen(
    navController: NavController
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Change the logo
        Text(
            "Hí anh em!!! This is our photos screen",
            style = MaterialTheme.typography.h5
        )
    }
}