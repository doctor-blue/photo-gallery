package com.devcomentry.photogallery.presention.all_file

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

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
        GlideImage(
            imageModel = "https://lh3.googleusercontent.com/W9Wg7CEvYdaVR5s6Z70aSFu8IAukRQrm9S6uEdhPw-YqTxoB-qYv-fFEsjPBW4VEXYtBIO-XOTHkkmy5wkXQdGZEQQ=w640-h400-e365-rj-sc0x00ffffff",
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            // shows an error ImageBitmap when the request failed.
        )
    }
}