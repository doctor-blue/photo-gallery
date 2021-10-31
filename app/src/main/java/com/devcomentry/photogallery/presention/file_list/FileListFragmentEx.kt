package com.devcomentry.photogallery.presention.file_list

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show
import com.devcomentry.photogallery.presention.utils.showDialogDelete


val FileListFragment.onItemSelected: (FileModel) -> Unit
    get() = {
        numFileSelected++
        updateFileSelectedToolbar()
    }

val FileListFragment.onItemUnselected: (FileModel) -> Unit
    get() = {
        numFileSelected--
        if (numFileSelected == 0)
            fileAdapter.isShowSelector = false
        updateFileSelectedToolbar()
    }

val FileListFragment.onItemClick: (FileModel) -> Unit
    get() = {
        safeNav(
            R.id.fileListFragment,
            FileListFragmentDirections.actionFileListFragmentToFullFileFragment(it.id, idFolder)
        )
    }

fun FileListFragment.unselectedAll() {
    fileAdapter.unselectedAll()
    numFileSelected = 0
    updateFileSelectedToolbar()
}


fun FileListFragment.updateFileSelectedToolbar(isShow: Boolean = false) {
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

fun FileListFragment.onToolbarItemClick(item: MenuItem) {
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
    }
}

fun FileListFragment.onSelectedToolbarItemClick(item: MenuItem) {
    when (item.itemId) {
        R.id.mnu_delete -> {
            dataLocal?.let {

            }
            requireContext().showDialogDelete(lifecycle, dataLocal, onCancel = {}) {
                unselectedAll()
                localDataViewModel.refreshData()
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

fun FileListFragment.showEmptyLisLayout(isVisible: Boolean) {
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