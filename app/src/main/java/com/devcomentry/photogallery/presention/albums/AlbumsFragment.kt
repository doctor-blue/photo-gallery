package com.devcomentry.photogallery.presention.albums

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAlbumsBinding
import com.devcomentry.photogallery.presention.albums.adapter.AlbumAdapter
import com.devcomentry.photogallery.presention.all_file.AllFileFragmentDirections
import com.devcomentry.photogallery.presention.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(R.layout.fragment_albums) {

    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter(requireContext(), onAlbumClick) }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner) { dataLocal ->
            val folderDetails = dataLocal.folder.map { folder ->

                val files = dataLocal.file.filter { it.idFolder == folder.id }
                FolderDetail(
                    folder,
                    files,
                    files.map { it.size }.sum()
                )
            }
//            folderSize = 0f

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
        safeNav(
            R.id.albumsFragment,
            AlbumsFragmentDirections.actionAlbumsFragmentToFileListFragment(
                idFolder = it.folder.id,
                it.folder.name
            )
        )
    }


}