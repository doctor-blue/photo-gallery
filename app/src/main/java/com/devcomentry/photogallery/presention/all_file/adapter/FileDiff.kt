package com.devcomentry.photogallery.presention.all_file.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel

class FileDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is FileModel && newItem is FileModel)
            oldItem.idDatabase == newItem.idDatabase
        else if (newItem is DateSelect && oldItem is DateSelect)
            oldItem.month == newItem.month
        else false
    }

    //    @SuppressLint("DiffUtilEquals")
//    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
//        return if (oldItem is FileModel && newItem is FileModel)
//            oldItem.name == newItem.name
//        else if (newItem is DateSelect && oldItem is DateSelect)
//            oldItem.month == newItem.month
//        else false
//    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if ((oldItem is FileModel && newItem is FileModel) || (newItem is DateSelect && oldItem is DateSelect))
            oldItem == newItem
        else false
    }
}