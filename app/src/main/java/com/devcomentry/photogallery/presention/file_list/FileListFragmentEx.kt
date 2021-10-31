package com.devcomentry.photogallery.presention.file_list

import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.MenuItem
import androidx.activity.result.IntentSenderRequest
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.*


val FileListFragment.onItemSelected: (FileModel) -> Unit
    get() = {
//        numFileSelected++
        updateFileSelectedToolbar()
    }

val FileListFragment.onItemUnselected: (FileModel) -> Unit
    get() = {
//        numFileSelected--
        updateFileSelectedToolbar()
        if (numFileSelected - 1 <= 0)
            fileAdapter.isShowSelector = false
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
    updateFileSelectedToolbar()
}


fun FileListFragment.updateFileSelectedToolbar(isShow: Boolean = false) {
    binding {
        numFileSelected = fileAdapter.currentList.filterIsInstance<FileModel>()
            .filter { it.isSelected }.size

        toolbarSelected.title =
            if (numFileSelected > 0) numFileSelected.toString() else requireContext().getString(R.string.select_items)
        if (numFileSelected > 0 || isShow) {
            ablSelected.show()
        } else {
            ablSelected.gone()
            fileAdapter.unselectedAll()
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
            if (numFileSelected > 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val sender = MediaStore.createDeleteRequest(
                        requireContext().contentResolver,
                        dataLocal?.file?.filter { it.isSelected }?.map { Uri.parse(it.uri) }
                            ?: listOf()
                    ).intentSender
                    try {
                        intentSenderLauncher.launch(
                            IntentSenderRequest.Builder(sender).build()
                        )
                    } catch (e: IntentSender.SendIntentException) {
                    }
                } else
                    requireContext().showDialogDelete(lifecycle, dataLocal, onCancel = {
                        requireContext().showToast(R.string.could_not_deleted_file_mess)
                    }) {
                        requireContext().showToast(R.string.file_deleted_mess)
                        unselectedAll()
                        localDataViewModel.refreshData()
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