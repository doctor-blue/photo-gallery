package com.devcomentry.photogallery.presention.all_file.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel

class FileDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is FileModel && newItem is FileModel)
            oldItem.name == newItem.name
        else if (newItem is DateSelect && oldItem is DateSelect)
            oldItem.month == newItem.month
        else false
    }
}