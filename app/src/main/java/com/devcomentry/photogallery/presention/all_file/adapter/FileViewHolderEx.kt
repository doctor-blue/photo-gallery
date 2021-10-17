package com.devcomentry.photogallery.presention.all_file.adapter

import com.devcomentry.photogallery.presention.utils.setPreventDoubleClick

fun FileAdapter.FileViewHolder.initEvents(){
    binding.root.setPreventDoubleClick {

    }

    binding.cbFile.setOnCheckedChangeListener { _, isSelected ->

    }
}