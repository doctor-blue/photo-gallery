package com.devcomentry.photogallery.data.data_source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devcomentry.photogallery.data.data_source.model.FolderEntity.Companion.TABLE_NAME

@Entity(tableName =TABLE_NAME)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_DATABASE)
    var idDatabase: Long = 0,

    @ColumnInfo(name = ID)
    var id: Long,

    @ColumnInfo(name = NAME)
    var name: String,

    @ColumnInfo(name = SIZE)
    var size: Int = 0,
    ) {

    companion object {
        const val TABLE_NAME = "FolderModelEntity"
        const val ID_DATABASE = "idDatabase"
        const val ID = "id"
        const val NAME = "name"
        const val SIZE = "size"
    }
}
