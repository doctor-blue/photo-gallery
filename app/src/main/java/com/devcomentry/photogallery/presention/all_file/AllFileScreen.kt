package com.devcomentry.photogallery.presention.all_file

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devcomentry.photogallery.presention.navigation.Screen
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.devcomentry.photogallery.presention.utils.Dimensions
import com.devcomentry.photogallery.presention.utils.lessThan
import com.devcomentry.photogallery.presention.utils.mediaQuery

@Composable
fun AllFileScreen(
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Change the logo
        Text(
            "Hí anh em!!! This is our photos screen",
            style = MaterialTheme.typography.h5
        )
      Box(
          modifier = Modifier.clickable {
              navController.navigate(Screen.PhotoDetailScreen.route)
          }
      ){
          GlideImage(
              imageModel = "https://lh3.googleusercontent.com/W9Wg7CEvYdaVR5s6Z70aSFu8IAukRQrm9S6uEdhPw-YqTxoB-qYv-fFEsjPBW4VEXYtBIO-XOTHkkmy5wkXQdGZEQQ=w640-h400-e365-rj-sc0x00ffffff",
              // Crop, Fit, Inside, FillHeight, FillWidth, None
              contentScale = ContentScale.Crop,
          )
      }
    }

    // * Media Query composable
//    MediaQuery(comparator = Dimensions.Width lessThan 400.dp) {
//        Text(
//            "Hí anh em!!! This is our photos screen",
//            style = MaterialTheme.typography.h5
//        )
//    }

      // * Media Query extension function with modifier
//    Text(
//        "Hí anh em!!! This is our photos screen",
//        style = MaterialTheme.typography.h5,
//        modifier = Modifier
//            .background(Color.Green)
//            .mediaQuery(
//                Dimensions.Width lessThan 400.dp,
//                modifier = Modifier
//                    .size(300.dp)
//            )
//    )
}