package com.devcomentry.photogallery.presention.navigation

import android.annotation.SuppressLint
import com.devcomentry.lib.ComposeScreen

sealed class Screen(_route: String) : ComposeScreen(_route) {
    object DummiesScreen : Screen("dummies_screen")
    @SuppressLint("CustomSplashScreen")
    object OurSplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")

}
