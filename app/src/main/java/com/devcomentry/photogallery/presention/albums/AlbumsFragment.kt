package com.devcomentry.photogallery.presention.albums

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAlbumsBinding
import com.devcomentry.photogallery.domain.model.Folder
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

        viewModel.dataLocal.observe(viewLifecycleOwner) {
            albumAdapter.submitList(it.folder)
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

    private val onAlbumClick: (Folder) -> Unit =  {
//        navController.navigate()
    }

}