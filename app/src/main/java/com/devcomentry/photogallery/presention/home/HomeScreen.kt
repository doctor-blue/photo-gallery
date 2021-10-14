package com.devcomentry.photogallery.presention.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.devcomentry.photogallery.presention.albums.AlbumsScreen
import com.devcomentry.photogallery.presention.home.components.BottomNavigationBar
import com.devcomentry.photogallery.presention.navigation.Screen
import com.devcomentry.photogallery.presention.all_file.AllFileScreen
import com.devcomentry.photogallery.presention.utils.PermissionUtils

@Composable
fun HomeScreen(navController: NavController) {

    PermissionUtils.checkPermission(LocalContext.current, {}, {})

//    val navController = rememberNavController()
    val currentScreen = remember { mutableStateOf<Screen>(Screen.AllFileScreen) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentScreen = currentScreen) {
                currentScreen.value = it
            }
        }
    )
    {
        if (currentScreen.value == Screen.AllFileScreen) {
            AllFileScreen(navController)
        } else {
            AlbumsScreen(navController)
        }
    }
}
