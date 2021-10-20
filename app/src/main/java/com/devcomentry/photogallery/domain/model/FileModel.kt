package com.devcomentry.photogallery.domain.model

data class FileModel(
    var id: Long = -1,
    var idFolder: Long,
    var type: Int,
    var name: String,
    var uri: String,
    var path: String,
    var size: Float = 0f,
    var duration: Long = 0L,
    var timeCreated: Long,
    var timeFile: String,
    var idDatabase: Long = 0,
    var linkThumb: String = "",
    var width: Long = 0L,
    var height: Long = 0L,
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false,
) {
    companion object {
        const val IS_IMAGE = 1
        const val IS_VIDEO = 2
    }
}
