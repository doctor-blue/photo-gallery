package com.devcomentry.photogallery.data.data_source.cache

import androidx.room.*
import com.devcomentry.photogallery.data.data_source.model.FileEntity
import com.devcomentry.photogallery.data.data_source.model.FileEntity.Companion.TABLE_NAME
import com.devcomentry.photogallery.data.data_source.model.FolderEntity

@Dao
interface FileDao {
    @Insert
    suspend fun addFile(fileEntity: FileEntity)

    @Update
    suspend fun updateFile(fileEntity: FileEntity)

    @Delete
    suspend fun removeFile(fileEntity: FileEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getFile(): List<FileEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE ${FileEntity.ID_DATABASE} = :id")
    suspend fun getFileById(id: Long): FileEntity?

    @Query("SELECT * FROM $TABLE_NAME WHERE ${FileEntity.TYPE} = :type")
    suspend fun getFileByType(type: Int): List<FileEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun removeAllFile()

    @Query("DELETE FROM ${FolderEntity.TABLE_NAME}")
    suspend fun removeAllFolder()

    @Query("SELECT * FROM ${FolderEntity.TABLE_NAME}")
    suspend fun getFolders(): List<FolderEntity>

    @Insert
    suspend fun addAllFile(files: List<FileEntity>)

    @Insert
    suspend fun addAllFolder(folders: List<FolderEntity>)


}