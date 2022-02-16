package com.devcomentry.photogallery.presentation.albums

import android.view.MenuItem
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.presentation.utils.gone
import com.devcomentry.photogallery.presentation.utils.show

fun AlbumsFragment.showEmptyLisLayout(isVisible: Boolean) {
    if (isVisible) {
        binding.apply {
            llListEmpty.show()
            rcvListAlbum.gone()
        }
    } else {
        binding {
            llListEmpty.gone()
            rcvListAlbum.show()
        }

    }
}

fun AlbumsFragment.onToolbarItemClick(item: MenuItem) {
    when (item.itemId) {
        R.id.mnu_new_folder -> {

        }
        R.id.mnu_reload_from_disk -> {
            localDataViewModel.refreshData()
        }
        R.id.mnu_settings -> {
            safeNav(R.id.albumsFragment, R.id.settingFragment)
        }
    }
}