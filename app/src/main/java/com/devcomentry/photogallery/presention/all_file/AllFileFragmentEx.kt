package com.devcomentry.photogallery.presention.all_file

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val AllFileFragment.onItemSelected: (FileModel) -> Unit
    get() = {
        numFileSelected++
        updateFileSelectedToolbar()
    }

val AllFileFragment.onItemUnselected: (FileModel) -> Unit
    get() = {
        numFileSelected--
        if (numFileSelected == 0)
            fileAdapter.isShowSelector = false
        updateFileSelectedToolbar()
    }

val AllFileFragment.onItemClick: (FileModel) -> Unit
    get() = {

    }

fun AllFileFragment.unselectedAll() {
    fileAdapter.unselectedAll()
    numFileSelected = 0
    updateFileSelectedToolbar()
}


fun AllFileFragment.updateFileSelectedToolbar(isShow: Boolean = false) {
    binding {
        toolbarSelected.title =
            if (numFileSelected > 0) numFileSelected.toString() else requireContext().getString(R.string.select_items)
        if (numFileSelected > 0 || isShow) {
            ablSelected.show()
        } else {
            ablSelected.gone()
        }
    }
}

fun AllFileFragment.onToolbarItemClick(item: MenuItem) {
    when (item.itemId) {
        R.id.mnu_select_items -> {
            fileAdapter.isShowSelector = true
            updateFileSelectedToolbar(true)
        }
//        R.id.mnu_favorites -> {
//
//        }
        R.id.mnu_reload_from_disk -> {
            localDataViewModel.refreshData()
        }
        R.id.mnu_settings -> {

        }
    }
}

fun AllFileFragment.onSelectedToolbarItemClick(item: MenuItem) {
    when (item.itemId) {
        R.id.mnu_delete -> {
            requireContext().showDialogDelete(lifecycle, {}) {
                dataLocal?.let { dataLocal ->
                    CoroutineScope(Dispatchers.Main).launch {
                        performDeleteImage(
                            dataLocal.file,
                            requireContext().applicationContext
                        ) {
                            unselectedAll()
                            localDataViewModel.refreshData()
                        }
                    }
                }
            }
        }
//        R.id.mnu_favorites -> {
//
//        }
        R.id.mnu_share -> {
            dataLocal?.let { dataLocal ->
                val imageUris = arrayListOf<Uri>()
                imageUris.addAll(dataLocal.file.filter { it.isSelected }.map { Uri.parse(it.uri) })

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND_MULTIPLE
                    putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                    type = "image/*"
                }
                startActivity(
                    Intent.createChooser(
                        shareIntent,
                        requireContext().getString(R.string.share_images_to)
                    )
                )
            }

        }
    }
}

fun AllFileFragment.showEmptyLisLayout(isVisible: Boolean) {
    if (isVisible) {
        binding {
            llListEmpty.show()
            rvAllFile.gone()
            toolbar.menu.findItem(R.id.mnu_select_items).isVisible = false
        }
    } else {
        binding {
            llListEmpty.gone()
            rvAllFile.show()
            toolbar.menu.findItem(R.id.mnu_select_items).isVisible = true
        }

    }
}

fun AllFileFragment.askStoragePermission() {
//    askPermissionForFirstTimes {
//        requireContext().showDialogAskPermission(lifecycle,onPause = {
//            countCheckPermission++
//        }) {
//            askPermission({
//                it.dismiss()
//            }) {
//                if (countCheckPermission >= 5) {
//                    it.dismiss()
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri = Uri.fromParts("package", requireContext().packageName, null)
//                    intent.data = uri
//                    requireContext().startActivity(intent)
//                }
//            }
//        }
//    }
    if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requireContext().showDialogAskPermission(lifecycle) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    localDataViewModel.refreshData()
                    it.dismiss()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // You can use the API that requires the permission.
                        localDataViewModel.refreshData()
                        it.dismiss()
                    } else {
                        it.dismiss()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        requireContext().startActivity(intent)
                    }

                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                }
            }

        }

    }
}

fun AllFileFragment.askPermissionForFirstTimes(onSuccess: () -> Unit = {}, onCancel: () -> Unit) {
    PermissionUtils.checkPermission(requireContext(), {
        localDataViewModel.refreshData()
        onSuccess()
    }, onCancel)
}

fun AllFileFragment.askPermission(onSuccess: () -> Unit = {}, onCancel: () -> Unit) {
    PermissionUtils.checkPermission(requireContext(), {
        localDataViewModel.refreshData()
        onSuccess()
    }, {
//        countCheckPermission++
//        if (countCheckPermission >= 5) {
//            dialog.dismiss()
//            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//            val uri = Uri.fromParts("package", requireContext().packageName, null)
//            intent.data = uri
//            requireContext().startActivity(intent)
//        }else{
//            askPermission(onSuccess, dialog)
//        }
        onCancel()
    })
}