package com.devcomentry.photogallery.data.data_source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devcomentry.photogallery.data.data_source.model.FileEntity

@Database(
    entities = [
        FileEntity::class,
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val fileDao: FileDao

    companion object {
        const val DB_NAME = "photos_db"
    }
}