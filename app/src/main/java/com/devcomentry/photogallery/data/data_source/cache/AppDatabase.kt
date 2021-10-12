package com.devcomentry.photogallery.data.data_source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devcomentry.photogallery.data.data_source.model.FileEntity
import com.devcomentry.photogallery.data.data_source.model.FolderEntity

@Database(
    entities = [
        FileEntity::class,
        FolderEntity::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val fileDao: FileDao

    companion object {
        const val DB_NAME = "photos_db"
    }
}