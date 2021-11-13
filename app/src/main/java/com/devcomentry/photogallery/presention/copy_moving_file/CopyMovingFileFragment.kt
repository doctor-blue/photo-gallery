package com.devcomentry.photogallery.presention.copy_moving_file

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toFile
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentCopyMovingFileBinding
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.model.Folder
import com.devcomentry.photogallery.presention.albums.FolderDetail
import com.devcomentry.photogallery.presention.albums.adapter.AlbumAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.showDialogCreateNewFolder
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
                val files = dataLocal.file.filter { it.idFolder == folder.id }
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

    private val onAlbumClick: (FolderDetail) -> Unit = { folderDetail ->
        if (folderDetail.folder.id == -2L) {
            context?.showDialogCreateNewFolder(
                lifecycle,
                onCancelClick,
                onCreateClick
            )
        }

        var isCopyAction = arguments?.getBoolean("isCopyAction") as Boolean

        if(isCopyAction) {
            // TODO COPY FILE
        } else {
            // TODO MOVE FILE
        }
    }

    private val onCancelClick: () -> Unit = {

    }

    private val onCreateClick: (String) -> Unit = { folderName ->
//        File("/storage/emulated/0/Pictures/IMG_20211023_002549.jpg").copyTo(File("/storage/emulated/0/Pictures/$folderName/IMG_20211023_002549.jpg"))
        val paths = arguments?.getStringArray("paths")
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        for (i in paths!!.indices) {
            File(paths[i]).copyTo(File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$folderName/a$i.jpg"))
        }
    }
}

// this is function for copying images
//val contentResolver = requireContext().contentResolver
//val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//} else {
//    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//}
//        for (i in uris!!.indices) {
//            val contentValue = ContentValues()
//            contentValue.put(MediaStore.Images.Media.DISPLAY_NAME, "$i.jpg")
//            contentValue.put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$folderName/")
//            val imageUri = contentResolver.insert(uri, contentValue)
//            val selectedUri = Uri.parse(uris.get(i).toString())
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedUri)
//            val fos = contentResolver.openOutputStream(imageUri!!)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//            fos?.close()
//        }