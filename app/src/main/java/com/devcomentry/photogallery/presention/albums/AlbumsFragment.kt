package com.devcomentry.photogallery.presention.albums

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAlbumsBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(R.layout.fragment_albums) {

    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter(requireContext(), onAlbumClick) }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner) { dataLocal ->
            val folderDetails = mutableListOf<FolderDetail>()
            dataLocal.folder.forEach { folder ->
                folderDetails.add(
                    FolderDetail(
                        folder,
                        dataLocal.file.filter { it.idFolder == folder.id }
                    )
                )
            }

            albumAdapter.submitList(folderDetails)
            showEmptyLisLayout(folderDetails.isEmpty())
            Log.d("Album", "initControls: ${dataLocal.folder.size}")
        }
        binding {
            rcvListAlbum.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = albumAdapter
            }

            toolbar.setOnMenuItemClickListener {
                onToolbarItemClick(it)
                true
            }
        }
    }

    override fun initEvents() {
        super.initEvents()
    }

    private val onAlbumClick: (FolderDetail) -> Unit = {
//        navController.navigate()
    }

    private fun showEmptyLisLayout(isVisible: Boolean) {
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

    private fun onToolbarItemClick(item: MenuItem) {
        when (item.itemId) {
            R.id.mnu_new_folder -> {

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
}