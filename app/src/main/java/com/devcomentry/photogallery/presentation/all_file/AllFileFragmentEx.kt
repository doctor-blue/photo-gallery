package com.devcomentry.photogallery.presentation.all_file

import android.net.Uri
import android.view.MenuItem
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presentation.utils.*


val AllFileFragment.onItemSelected: (FileModel) -> Unit
    get() = {
//        numFileSelected++
        updateFileSelectedToolbar()
    }

val AllFileFragment.onItemUnselected: (FileModel) -> Unit
    get() = {
//        numFileSelected--
        updateFileSelectedToolbar()
        if (numFileSelected == 0)
            fileAdapter.isShowSelector = false
    }

val AllFileFragment.onItemClick: (FileModel) -> Unit
    get() = {
        safeNav(
            R.id.allFileFragment,
            AllFileFragmentDirections.actionAllFileFragmentToFullFileFragment(it.id)
        )
    }

fun AllFileFragment.unselectedAll() {
    fileAdapter.unselectedAll()
    updateFileSelectedToolbar()
}


fun AllFileFragment.updateFileSelectedToolbar(isShow: Boolean = false) {
    binding {
        numFileSelected = fileAdapter.currentList.filterIsInstance<FileModel>()
            .filter { it.isSelected }.size

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
            safeNav(
                R.id.allFileFragment,
                AllFileFragmentDirections.actionAllFileFragmentToSettingFragment()
            )
        }
    }
}

fun AllFileFragment.onSelectedToolbarItemClick(item: MenuItem) {
    when (item.itemId) {
        R.id.mnu_delete -> {
            if (numFileSelected > 0) {
                performDeleteImage(
                    dataLocal?.file?.filter { it.isSelected } ?: listOf(),
                    requireContext(),
                    intentSenderLauncher,
                    lifecycle = lifecycle
                ) {
                    unselectedAll()
                    localDataViewModel.refreshData()
                }
            }
        }
//        R.id.mnu_favorites -> {
//
//        }
        R.id.mnu_share -> {
            if (numFileSelected > 0)

                dataLocal?.let { dataLocal ->
                    val imageUris = arrayListOf<Uri>()
                    imageUris.addAll(dataLocal.file.filter { it.isSelected }
                        .map { Uri.parse(it.uri) })

                    shareImageTo(imageUris, requireContext())
                }

        }
        R.id.mnu_select_all -> {
            fileAdapter.selectAll()
            updateFileSelectedToolbar()
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