package com.devcomentry.photogallery.presentation.albums

import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.model.Folder

data class FolderDetail(
    var folder: Folder,
    var imageFiles: List<FileModel>,
    var folderSize: Float
)
