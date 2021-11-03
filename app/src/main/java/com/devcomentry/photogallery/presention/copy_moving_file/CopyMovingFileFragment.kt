package com.devcomentry.photogallery.presention.copy_moving_file

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentCopyMovingFileBinding
import com.devcomentry.photogallery.domain.model.Folder
import com.devcomentry.photogallery.presention.albums.FolderDetail
import com.devcomentry.photogallery.presention.albums.adapter.AlbumAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment
import java.io.File
import java.net.URI

class CopyMovingFileFragment : BaseFragment<FragmentCopyMovingFileBinding>(R.layout.fragment_copy_moving_file) {

    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter(requireContext(), onAlbumClick) }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner) { dataLocal ->
            val newFolder = FolderDetail(
                Folder(
                    id = -2,
                    name = getString(R.string.new_folder),
                    size = -1,
                    showFull = false,
                    idDatabase = -1
                ),
                listOf(),
                -1f
            )
            val folderList = mutableListOf(newFolder)
            val folderDetails = dataLocal.folder.map { folder ->
                val files = dataLocal.file.filter { it.idFolder == folder.id }.toMutableList()
                FolderDetail(
                    folder,
                    files,
                    files.map { it.size }.sum()
                )
            }
            folderList.addAll(folderDetails)
            albumAdapter.submitList(folderList)
            Log.d("Album", "initControls: ${folderList.toString()}")
        }
        binding {
            rcvListAlbum.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = albumAdapter
            }

            toolbar.setOnMenuItemClickListener {
//                onToolbarItemClick(it)
                true
            }
        }
    }

    override fun initEvents() {
        super.initEvents()

    }

    private val onAlbumClick: (FolderDetail) -> Unit = {

        var path = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}"
        Log.d("URI", path)
        val folder = File("$path/Abc")
        folder.mkdir()

    }

}