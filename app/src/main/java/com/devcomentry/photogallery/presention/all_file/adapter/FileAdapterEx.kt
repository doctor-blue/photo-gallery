package com.devcomentry.photogallery.presention.all_file.adapter

import androidx.core.view.setPadding
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.convertDpToPixel
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.setPreventDoubleClick
import com.devcomentry.photogallery.presention.utils.show

fun FileAdapter.FileViewHolder.initEvents() {
    binding.root.setPreventDoubleClick {
        onItemClick(!fileModel!!.isSelected)
    }

    binding.root.setOnLongClickListener {
        showAllSelector()
        onItemClick(!fileModel!!.isSelected)
        true
    }

//    binding.cbFile.setOnCheckedChangeListener { _, isSelected ->
//        fileModel!!.isSelected = isSelected
//        if (isSelected) {
//            binding.root.setBackgroundResource(R.color.blue_200_alpha)
//            binding.root.setPadding(binding.root.context.convertDpToPixel(8f).toInt())
//        } else {
//            binding.root.background = null
//            binding.root.setPadding(0)
//        }
//    }
    binding.cbFile.setPreventDoubleClick {
        onItemClick(!fileModel!!.isSelected)
    }
}

fun FileAdapter.FileViewHolder.showSelector(fileModel: FileModel, isShowSelector: Boolean) {
    binding.cbFile.isChecked = fileModel.isSelected
    if (isShowSelector) {
        binding.viewBlur.show()
        binding.cbFile.show()
    } else {
        binding.viewBlur.gone()
        binding.cbFile.gone()
    }

    if (fileModel.isSelected) {
        binding.llImage.setBackgroundResource(R.color.blue_200_alpha)
        binding.llImage.setPadding(binding.root.context.convertDpToPixel(8f).toInt())
    } else {
        binding.llImage.background = null
        binding.llImage.setPadding(0)
    }
}