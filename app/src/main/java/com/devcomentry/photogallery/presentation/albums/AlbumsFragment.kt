package com.devcomentry.photogallery.presentation.albums

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAlbumsBinding
import com.devcomentry.photogallery.presentation.albums.adapter.AlbumAdapter
import com.devcomentry.photogallery.presentation.common.BaseFragment
import com.devcomentry.photogallery.presentation.utils.HideBottomNavEvent
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

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

            rcvListAlbum.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        EventBus.getDefault().post(HideBottomNavEvent(true))
                    } else {
                        EventBus.getDefault().post(HideBottomNavEvent(false))
                    }
                }
            })
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