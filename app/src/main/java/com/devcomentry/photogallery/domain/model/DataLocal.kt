package com.devcomentry.photogallery.domain.model

import java.time.Month

data class DataLocal(
    val file: MutableList<FileModel> = mutableListOf(),
    val folder: MutableList<Folder> = mutableListOf(),
    val listDate: MutableList<DateSelect> = mutableListOf(),
   val listMonth: MutableList<DateSelect> = mutableListOf()
)
