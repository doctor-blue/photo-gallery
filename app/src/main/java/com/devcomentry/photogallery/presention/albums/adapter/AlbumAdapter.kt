package com.devcomentry.photogallery.presention.albums.adapter

import android.content.Context
import android.view.ViewGroup
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.bumptech.glide.Glide
import com.devcomentry.moonlight.binding.BindingListAdapter
import com.devcomentry.moonlight.binding.BindingViewHolder
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.AlbumFolderItemBinding
import com.devcomentry.photogallery.presention.albums.FolderDetail
import com.devcomentry.photogallery.presention.utils.decimalFormat
import com.devcomentry.photogallery.presention.utils.setPreventDoubleClick

class AlbumAdapter(
    private val context: Context,
    private val onClick: (FolderDetail) -> Unit
) : BindingListAdapter<FolderDetail, AlbumFolderItemBinding>(R.layout.album_folder_item) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(getBinding(parent), context)
    }

    inner class AlbumViewHolder(private val binding: AlbumFolderItemBinding, context: Context) :
        BindingViewHolder<FolderDetail, AlbumFolderItemBinding>(binding) {

        override fun onBind(item: FolderDetail) {
            binding.apply {
                val fileCount = item.imageFiles.size
                var folderSize = item.folderSize
                var unit = context.getStringArray(R.array.memory_unit)[0]
                val oneGbFromB = 1_073_741_824.0f
                val oneMBFromB = 1_048_576.0f

                root.setPreventDoubleClick {
                    onClick(item)
                }
                txtFolderName.text = item.folder.name

                if (item.imageFiles.size > 1)
                    txtFileCount.text = ("$fileCount ${context.getString(R.string.files)}")
                else
                    txtFileCount.text = ("$fileCount ${context.getString(R.string.file)}")
                Glide.with(context).load(item.imageFiles[0].uri).into(imgFile)
                // >1MB dv = MB, <1MB dv = KB
                if (folderSize >= oneMBFromB && folderSize < oneGbFromB) {
                    folderSize /= oneMBFromB
                    unit = context.getStringArray(R.array.memory_unit)[1]
                } else if (folderSize >= oneGbFromB) {
                    folderSize /= oneGbFromB
                    unit = context.getStringArray(R.array.memory_unit)[2]
                } else {
                    folderSize /= 1024
                }

                txtFolderSize.text = (folderSize.decimalFormat() + unit)
            }
        }
    }

}