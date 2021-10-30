package com.devcomentry.photogallery.presention.albums

import android.view.MenuItem
import android.widget.Toast
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

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
            Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show()
        }
    }
}