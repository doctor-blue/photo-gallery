package com.devcomentry.photogallery.presention.home.components

import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.presention.navigation.Screen

sealed class NavigationItem(var screen: Screen, var icon: Int, var title: Int) {
    object AllFile :
        NavigationItem(Screen.AllFileScreen, R.drawable.ic_photos, R.string.photos)

    object AlbumsScreen :
        NavigationItem(Screen.AlbumsScreen, R.drawable.ic_folder, R.string.albums)
}