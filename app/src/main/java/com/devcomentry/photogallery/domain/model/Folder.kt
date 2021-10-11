package com.devcomentry.photogallery.domain.model

data class Folder(
    val id: Long,
    val name: String,
    var size: Int,
    var showFull: Boolean = false,
)