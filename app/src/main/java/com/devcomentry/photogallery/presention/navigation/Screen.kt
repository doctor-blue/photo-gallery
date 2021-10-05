package com.devcomentry.photogallery.presention.navigation

import com.devcomentry.lib.ComposeScreen

sealed class Screen(_route:String):ComposeScreen(_route){
    object DummiesScreen:Screen("dummies_screen")
}
