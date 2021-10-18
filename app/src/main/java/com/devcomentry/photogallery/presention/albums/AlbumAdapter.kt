package com.devcomentry.photogallery.presention.albums

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.devcomentry.moonlight.binding.BindingListAdapter
import com.devcomentry.moonlight.binding.BindingViewHolder
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.AlbumFolderItemBinding

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
                folderName.text = item.folder.name
                folderSize.text = item.folder.size.toString()
                Glide.with(context).load(item.imageFiles[0].uri).into(folderImage)
            }
        }
    }

}