package com.devcomentry.photogallery.presention.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devcomentry.lib.composable
import com.devcomentry.photogallery.presention.dummies.DummiesScreen

@Composable
fun Navigation(

) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.DummiesScreen.route
    ) {

        composable(
            screen = Screen.DummiesScreen
        ) {
            DummiesScreen(navController)
        }
    }
}