package com.devcomentry.photogallery.presention.home

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcomentry.photogallery.presention.albums.AlbumsScreen
import com.devcomentry.photogallery.presention.home.components.BottomNavigationBar
import com.devcomentry.photogallery.presention.navigation.Screen
import com.devcomentry.photogallery.presention.all_file.AllFileScreen
import com.devcomentry.photogallery.presention.navigation.Navigation
import com.devcomentry.photogallery.presention.utils.PermissionUtils

@Composable
fun HomeScreen() {

    PermissionUtils.checkPermission(LocalContext.current, {}, {})

//    val navController = rememberNavController()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.AllFileScreen.route || currentRoute == Screen.AlbumsScreen.route) {
                BottomNavigationBar(navController)
            }
        }
    )
    {
      Navigation(navController)
    }
}
