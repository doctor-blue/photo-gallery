package com.devcomentry.photogallery.presention

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.devcomentry.photogallery.presention.home.HomeScreen
import com.devcomentry.photogallery.presention.home.components.BottomNavigationBar
import com.devcomentry.photogallery.presention.navigation.Navigation
import com.devcomentry.photogallery.ui.theme.GalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}