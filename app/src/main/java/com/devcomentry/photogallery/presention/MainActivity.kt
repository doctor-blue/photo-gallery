package com.devcomentry.photogallery.presention

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devcomentry.moonlight.binding.BindingActivity
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.ActivityMainBinding
import com.devcomentry.photogallery.presention.utils.HideBottomNavEvent
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    companion object {
        var isInit = false
    }

    override fun initEvents() {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.allFileFragment || destination.id == R.id.albumsFragment) {
                onHideBottomNavEvent(HideBottomNavEvent(false))
                binding.bottomBar.show()
//                Log.d("MainActivity", "show + ${binding.bottomBar.isShow}")
            } else {
                if (isInit) {
                    onHideBottomNavEvent(HideBottomNavEvent(true))
                }
            }
        }

    }

    override fun initControls(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.main_fragment)
//        binding.bottomBar.setupWithNavController(navController)
        binding.bottomBar.setupWithNavController(navController)
    }

    private var lastScrollTime: Long = 0
    private var hideBottomNavDuration = 500L
    private val bottomNavY: Float by lazy {
        binding.bottomBar.y
    }

    @Subscribe
    fun onHideBottomNavEvent(event: HideBottomNavEvent) {
        if (event.isShow) {
            if (SystemClock.elapsedRealtime() - lastScrollTime < 300) return
            lastScrollTime = SystemClock.elapsedRealtime()
            binding.bottomBar.animate().translationY(bottomNavY).duration =
                hideBottomNavDuration + 200
        } else {
            if (SystemClock.elapsedRealtime() - lastScrollTime < 300) return
            lastScrollTime = SystemClock.elapsedRealtime()
            binding.bottomBar.animate().translationY(0f).duration = hideBottomNavDuration - 150
        }
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        navController.navigateUp()
        return true
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}