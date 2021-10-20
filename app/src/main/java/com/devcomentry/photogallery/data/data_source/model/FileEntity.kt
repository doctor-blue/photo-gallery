package com.devcomentry.photogallery.data.data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devcomentry.photogallery.data.data_source.model.FileEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FileEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_DATABASE)
    var idDatabase: Long = 0,

    @ColumnInfo(name = ID)
    var id: Long,

    @ColumnInfo(name = ID_FOLDER)
    var idFolder: Long,

    @ColumnInfo(name = TYPE)
    var type: Int,

    @ColumnInfo(name = NAME)
    var name: String,

    @ColumnInfo(name = URI)
    var uri: String,

    @ColumnInfo(name = PATH)
    var path: String,

    @ColumnInfo(name = SIZE)
    var size: Float = 0f,

    @ColumnInfo(name = DURATION)
    var duration: Long = 0L,

    @ColumnInfo(name = TIME_CREATED)
    var timeCreated: Long,

    @ColumnInfo(name = TIME_FILE)
    var timeFile: String,

    @ColumnInfo(name = LINK_THUMB)
    var linkThumb: String,

    @ColumnInfo(name = WIDTH)
    var width: Long = 0L,

    @ColumnInfo(name = HEIGHT)
    var height: Long = 0L,

    @ColumnInfo(name = IS_FAVORITE)
    var isFavorite: Boolean = false,

) {

    companion object {
        const val TABLE_NAME = "FileModelEntity"
        const val ID_DATABASE = "idDatabase"
        const val ID = "id"
        const val ID_FOLDER = "idFolder"
        const val TYPE = "type"
        const val NAME = "name"
        const val URI = "uri"
        const val PATH = "path"
        const val SIZE = "size"
        const val DURATION = "duration"
        const val TIME_CREATED = "timeCreated"
        const val TIME_FILE = "timeFile"
        const val LINK_THUMB = "linkThumb"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IS_FAVORITE = "isFavorite"

    }

}