package com.devcomentry.photogallery.presention.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devcomentry.lib.composable
import com.devcomentry.photogallery.presention.albums.AlbumsScreen
import com.devcomentry.photogallery.presention.all_file.AllFileScreen
import com.devcomentry.photogallery.presention.home.HomeScreen
import com.devcomentry.photogallery.presention.photo_detail.PhotoDetailScreen
import com.devcomentry.photogallery.presention.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
//    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.OurSplashScreen.route
    ) {

        composable(
            screen = Screen.OurSplashScreen
        ) {
            SplashScreen(navController)
        }

        composable(
            screen = Screen.PhotoDetailScreen
        ) {
            PhotoDetailScreen(navController)
        }

        composable(
            screen = Screen.AlbumsScreen
        ) {
            AlbumsScreen(navController)
        }
        composable(
            screen = Screen.AllFileScreen
        ) {
            AllFileScreen(navController)
        }
    }
}