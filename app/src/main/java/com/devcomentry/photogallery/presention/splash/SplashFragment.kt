package com.devcomentry.photogallery.presention.splash

import android.os.Bundle
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentSplashBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import kotlinx.coroutines.*

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            withContext(Dispatchers.Main){
                safeNav(R.id.splashFragment, SplashFragmentDirections.actionSplashFragmentToAllFileFragment())

            }
        }
    }
}