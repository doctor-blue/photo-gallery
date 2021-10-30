package com.devcomentry.photogallery.presention.photo_detail.adapter

import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.Constants
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

fun FullFileAdapter.FullFileViewHolder.initEvents() {
    with(binding) {
        imgFull.setOnClickListener {
            setDetailVisible()
        }
    }
}

fun FullFileAdapter.FullFileViewHolder.setData(fileModel: FileModel) {
    with(binding) {
        txtDate.text = Constants.formatter.format(fileModel.timeCreated)
        txtName.text = fileModel.name
    }
}

fun FullFileAdapter.FullFileViewHolder.setFunctionButtonVisible(isVisible: Boolean) {
    with(binding) {
        if (isVisible) {
            imvBack.gone()
            txtName.gone()
            txtDate.gone()
            imvDelete.gone()
            imvShare.gone()
            imvDetail.gone()
            imvLock.gone()
        } else {
            imvBack.show()
            txtName.show()
            txtDate.show()
            imvDelete.show()
            imvShare.show()
            imvDetail.show()
            imvLock.show()
        }
    }
}