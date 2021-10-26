package com.devcomentry.photogallery.presention.utils

import android.Manifest
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object PermissionUtils {
//    fun checkPermission(context: Context, permission: String) {
//        Dexter.withContext(context)
//            .withPermissions(
//                permisstion,
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: MutableList<PermissionRequest>?,
//                    token: PermissionToken?
//                ) {
//                    token?.continuePermissionRequest()
//                }
//            })
//            .check()
//    }
//
//    fun checkPermission2(context: Context, permission: String) {
//        Dexter.withContext(context)
//            .withPermissions(
//                permission,
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                    val lm: LocationManager =
//                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                    var gpsEnabled = false
//                    var network_enabled = false
//
//                    try {
//                        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                    } catch (ex: Exception) {
//                    }
//
//                    if (!gpsEnabled) {
//                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                    }
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: MutableList<PermissionRequest>?,
//                    token: PermissionToken?
//                ) {
//                    token?.continuePermissionRequest()
//                }
//            })
//            .check()
//    }
//
//    var listPermission: MutableList<Permission> = mutableListOf()
//
//    @JvmName("getlistPermission1")
//    fun getlistPermission(context: Context): MutableList<Permission> {
//        if (listPermission.isEmpty()) {
//            listPermission.add(
//                Permission(
//                    context.getString(R.string.grant_storage_permission),
//                    context.getString(R.string.grant_storage_permission),
//                    false
//                )
//            )
////        listPermission.add(
////            Permission(
////                context.getString(R.string.modify_settings),
////                context.getString(R.string.description),
////                false
////            )
////        )
//        }
//        return listPermission
//    }

    fun checkPermission(context: Context, onSuccess: () -> Unit={}, onCancel: () -> Unit={}) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            onSuccess()
                        } else {
                            onCancel()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }


//    fun checkAllPermisstion(context: Context): Boolean {
//        val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        var check = false
//        val lm: LocationManager =
//            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        var gps_enabled = false
//        var network_enabled = false
//
//        try {
//            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        } catch (ex: Exception) {
//        }
//
//        check = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED && gps_enabled
//
////        if (ContextCompat.checkSelfPermission(
////                context,
////                Manifest.permission.CAMERA
////            ) == PackageManager.PERMISSION_GRANTED
////        ) {
////
////            if(check){
////                check = true
////            }
////
////        }else{
////            check = false
////        }
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            if (Settings.System.canWrite(context)) {
//                if (check) {
//                    check = true
//                }
//
//            } else {
//
//                check = false
//            }
//        }
//
//
////        if( Settings.canDrawOverlays(context)){
////            if(check){
////                check = true
////            }
////        }else{
////            check = false
////
////        }
////
////
////        val wifiMgr =
////            context.getSystemService(Context.WIFI_SERVICE) as WifiManager
////        if (wifiMgr.isWifiEnabled) { // Wi-Fi adapter is ON
////            if(check){
////                check = true
////            }
////        }else{
////            check = false
////        }
////
////        if (bluetoothAdapter.isEnabled()) {
////            if(check){
////                check = true
////            }
////
////        }else{
////            check = false
////        }
//
//        return check
//    }
}