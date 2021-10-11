package com.devcomentry.photogallery.domain.model

data class DataLocal(
    val file: MutableList<FileModel> = mutableListOf(),
    val folder: MutableList<Folder> = mutableListOf(),
    val listDate: MutableList<DateSelect> = mutableListOf()
)
