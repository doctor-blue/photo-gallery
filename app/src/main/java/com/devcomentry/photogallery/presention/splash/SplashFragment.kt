package com.devcomentry.photogallery.presention.splash

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentSplashBinding
import com.devcomentry.photogallery.presention.MainActivity
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.getStoragePermission
import kotlinx.coroutines.*

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        getStoragePermission()
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    safeNav(
                        R.id.splashFragment,
                        SplashFragmentDirections.actionSplashFragmentToAllFileFragment()
                    )
                    MainActivity.isInit = true
                } else
                    safeNav(
                        R.id.splashFragment,
                        SplashFragmentDirections.actionSplashFragmentToPermissionFragment()
                    )
            }
        }
    }
}