package com.devcomentry.photogallery.presention.albums

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAlbumsBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.common.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<FragmentAlbumsBinding>(R.layout.fragment_albums) {

    private val viewModel: LocalDataViewModel by viewModels()
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter(requireContext(), onAlbumClick) }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        viewModel.getData()

        viewModel.dataLocal.observe(viewLifecycleOwner) { dataLocal ->
            val folderDetails = mutableListOf<FolderDetail>()
            dataLocal.folder.forEach { folder ->
                folderDetails.add(
                    FolderDetail(
                        folder,
                        dataLocal.file.filter { it.idFolder == folder.id }))
            }
            albumAdapter.submitList(folderDetails)
        }

        binding.rcvListAlbum.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = albumAdapter
        }

    }

    override fun initEvents() {
        super.initEvents()
    }

    private val onAlbumClick: (FolderDetail) -> Unit = {
//        navController.navigate()
    }

}