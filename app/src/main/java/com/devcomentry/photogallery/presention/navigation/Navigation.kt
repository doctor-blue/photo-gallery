package com.devcomentry.photogallery.presention.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devcomentry.lib.composable
import com.devcomentry.photogallery.presention.home.HomeScreen
import com.devcomentry.photogallery.presention.splash.SplashScreen

@Composable
fun Navigation(
) {
    val navController = rememberNavController()
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
            screen = Screen.HomeScreen
        ) {
            HomeScreen(navController)
        }

//        composable(
//            screen = Screen.AlbumsScreen
//        ) {
//            AlbumsScreen(navController)
//        }
//
//        composable(
//            screen = Screen.AllFileScreen
//        ) {
//            AllFileScreen(navController)
//        }
    }
}