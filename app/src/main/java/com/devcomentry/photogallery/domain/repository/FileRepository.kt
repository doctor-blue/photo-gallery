package com.devcomentry.photogallery.domain.repository

import com.devcomentry.photogallery.domain.model.FileModel

interface FileRepository {
    suspend fun getFile(): List<FileModel>

    suspend fun getFileById(id: Long): FileModel?

    suspend fun getFileByType(type: Int): List<FileModel>

    suspend fun updateFile(fileModel: FileModel)

    suspend fun addFile(fileModel: FileModel)

    suspend fun removeFile(fileModel: FileModel)
}