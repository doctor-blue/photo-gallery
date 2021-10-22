package com.devcomentry.photogallery.presention.all_file

import android.view.MenuItem
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

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
        R.id.mnu_favorites -> {

        }
        R.id.mnu_reload_from_disk -> {
            localDataViewModel.refreshData()
        }
        R.id.mnu_settings -> {

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