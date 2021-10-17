package com.devcomentry.photogallery.presention.albums

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devcomentry.moonlight.binding.BindingListAdapter
import com.devcomentry.moonlight.binding.BindingViewHolder
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.AlbumFolderItemBinding
import com.devcomentry.photogallery.domain.model.Folder

class AlbumAdapter(
    private val context: Context,
    private val onClick: (Folder) -> Unit
): BindingListAdapter<Folder, AlbumFolderItemBinding>(R.layout.album_folder_item) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(getBinding(parent))
    }

    inner class AlbumViewHolder(private val binding: AlbumFolderItemBinding): BindingViewHolder<Folder, AlbumFolderItemBinding>(binding) {

        override fun onBind(item: Folder) {
            binding.folderName.text = item.name
            binding.folderSize.text = item.size.toString()
        }
    }

}