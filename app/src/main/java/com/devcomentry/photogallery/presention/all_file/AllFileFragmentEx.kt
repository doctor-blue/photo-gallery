package com.devcomentry.photogallery.presention.all_file

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
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
            safeNav(R.id.allFileFragment, AllFileFragmentDirections.actionAllFileFragmentToSettingFragment())
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