package com.devcomentry.photogallery.presention

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.devcomentry.moonlight.binding.BindingActivity
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.ActivityMainBinding
import com.devcomentry.photogallery.presention.utils.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    companion object {
        var isInit = false
    }

    override fun initEvents() {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.allFileFragment || destination.id == R.id.albumsFragment) {
                binding.bottomBar.show()
                binding.bottomBar.visibility = View.VISIBLE
                Log.d("MainActivity", "show + ${binding.bottomBar.isShow}")
            } else {
                if (isInit) {
                    binding.bottomBar.hide()
                    if (destination.id == R.id.fullFileFragment) {
                        binding.bottomBar.gone()
                    }
                }
                Log.d("MainActivity", "hide + ${binding.bottomBar.isShow}")
            }
        }

    }

    override fun initControls(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.main_fragment)
        binding.bottomBar.setupWithNavController(navController)
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        navController.navigateUp()
        return true
    }


}