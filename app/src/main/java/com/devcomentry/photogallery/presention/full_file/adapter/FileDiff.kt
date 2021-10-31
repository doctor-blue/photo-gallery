package com.devcomentry.photogallery.presention.full_file.adapter

import androidx.recyclerview.widget.DiffUtil
import com.devcomentry.photogallery.domain.model.FileModel

class FileDiff : DiffUtil.ItemCallback<FileModel>() {
    override fun areItemsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem.uri == newItem.uri
    }
}