package com.devcomentry.photogallery.di

import com.devcomentry.photogallery.data.data_source.cache.FileDao
import com.devcomentry.photogallery.data.data_source.mappers.FileEntityMapper
import com.devcomentry.photogallery.data.data_source.mappers.FolderEntityMapper
import com.devcomentry.photogallery.data.repository.FileRepositoryImpl
import com.devcomentry.photogallery.domain.repository.FileRepository
import com.devcomentry.photogallery.domain.use_case.file.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideFileMapper(): FileEntityMapper = FileEntityMapper()

    @Provides
    @Singleton
    fun provideFolderMapper(): FolderEntityMapper = FolderEntityMapper()

    @Provides
    @Singleton
    fun provideRepository(
        fileDao: FileDao,
        fileEntityMapper: FileEntityMapper,
        folderEntityMapper: FolderEntityMapper,
    ): FileRepository = FileRepositoryImpl(
        fileDao,
        fileEntityMapper,
        folderEntityMapper
    )

    @Provides
    @Singleton
    fun provideUseCases(fileRepository: FileRepository): FileUseCases = FileUseCases(
        addFile = AddFile(fileRepository),
        updateFile = UpdateFile(fileRepository),
        removeFile = RemoveFile(fileRepository),
        getFileById = GetFileById(fileRepository),
        getFileByType = GetFileByType(fileRepository),
        clearData = ClearData(fileRepository),
        addFolder = AddFolder(fileRepository)
    )

}