package com.devcomentry.photogallery.presention.albums.adapter

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.devcomentry.moonlight.binding.BindingListAdapter
import com.devcomentry.moonlight.binding.BindingViewHolder
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.AlbumFolderItemBinding
import com.devcomentry.photogallery.presention.albums.FolderDetail
import com.devcomentry.photogallery.presention.utils.setPreventDoubleClick

class AlbumAdapter(
    private val context: Context,
    private val onClick: (FolderDetail) -> Unit
) : BindingListAdapter<FolderDetail, AlbumFolderItemBinding>(R.layout.album_folder_item) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(getBinding(parent))
    }

    inner class AlbumViewHolder(private val binding: AlbumFolderItemBinding) :
        BindingViewHolder<FolderDetail, AlbumFolderItemBinding>(binding) {

        override fun onBind(item: FolderDetail) {
            binding.apply {
                root.setPreventDoubleClick {
                    onClick(item)
                }
                txtFolderName.text = item.folder.name
                if (item.imageFiles.size > 1)
                    txtFileCount.text = (item.imageFiles.size.toString() + " files")
                else
                    txtFileCount.text = (item.imageFiles.size.toString() + " file")
                Glide.with(context).load(item.imageFiles[0].uri).into(imgFile)
                // >1MB dv = MB, <1MB dv = KB
                txtFolderSize.text = (item.folderSize.toString() + " MB")
            }
        }
    }

}