package com.devcomentry.photogallery.presentation.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.PermissionFragmentBinding
import com.devcomentry.photogallery.presentation.MainActivity
import com.devcomentry.photogallery.presentation.common.BaseFragment
import com.devcomentry.photogallery.presentation.utils.PermissionUtils
import com.devcomentry.photogallery.presentation.utils.getStoragePermission
import com.devcomentry.photogallery.presentation.utils.setPreventDoubleClick

class PermissionFragment : BaseFragment<PermissionFragmentBinding>(R.layout.permission_fragment) {
    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                getStoragePermission()

            ) == PackageManager.PERMISSION_GRANTED
        ) {
            MainActivity.isInit = true
            safeNav(
                R.id.permissionFragment,
                PermissionFragmentDirections.actionPermissionFragmentToAllFileFragment()
            )
        }
    }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)
        binding.tvOk.setPreventDoubleClick {
            askStoragePermission()
        }
    }

    private fun askStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                getStoragePermission()
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    getStoragePermission()

                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                }
                shouldShowRequestPermissionRationale(getStoragePermission()) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            getStoragePermission()

                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // You can use the API that requires the permission.
                    } else {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        requireContext().startActivity(intent)
                    }
                }
                else -> {
                    PermissionUtils.checkPermission(requireContext())
                }
            }
        } else {
            MainActivity.isInit = true
            safeNav(
                R.id.permissionFragment,
                PermissionFragmentDirections.actionPermissionFragmentToAllFileFragment()
            )
        }
    }

}