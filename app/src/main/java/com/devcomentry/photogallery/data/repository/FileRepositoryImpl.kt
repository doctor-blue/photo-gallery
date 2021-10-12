package com.devcomentry.photogallery.data.repository

import com.devcomentry.photogallery.data.data_source.cache.FileDao
import com.devcomentry.photogallery.data.data_source.mappers.FileEntityMapper
import com.devcomentry.photogallery.data.data_source.mappers.FolderEntityMapper
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.model.Folder
import com.devcomentry.photogallery.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDao: FileDao,
    private val fileEntityMapper: FileEntityMapper,
    private val folderEntityMapper: FolderEntityMapper,
) : FileRepository {

    override suspend fun getFile(): List<FileModel> = fileDao.getFile().let {
        fileEntityMapper.toDomainList(it)
    }

    override suspend fun getFileById(id: Long): FileModel? = fileDao.getFileById(id)?.let {
        fileEntityMapper.toDomain(it)
    }

    override suspend fun getFileByType(type: Int): List<FileModel> =
        fileDao.getFileByType(type).let {
            fileEntityMapper.toDomainList(it)
        }

    override suspend fun updateFile(fileModel: FileModel) = fileDao.updateFile(
        fileEntityMapper.fromDomain(fileModel)
    )

    override suspend fun addFile(fileModel: FileModel) = fileDao.addFile(
        fileEntityMapper.fromDomain(fileModel)
    )

    override suspend fun removeFile(fileModel: FileModel) = fileDao.removeFile(
        fileEntityMapper.fromDomain(fileModel)
    )

    override suspend fun clearFile() {
        fileDao.removeAllFile()
        fileDao.removeAllFolder()
    }

    override suspend fun getFolder() = fileDao.getFolders().let {
        folderEntityMapper.toDomainList(it)
    }

    override suspend fun addAllFile(files: List<FileModel>) {
        fileDao.addAllFile(
            fileEntityMapper.fromDomainList(files)
        )
    }

    override suspend fun addAllFolder(folders: List<Folder>) {
        fileDao.addAllFolder(
            folderEntityMapper.fromDomainList(folders)
        )
    }
}