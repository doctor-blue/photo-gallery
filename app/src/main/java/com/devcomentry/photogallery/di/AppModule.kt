package com.devcomentry.photogallery.di

import com.devcomentry.photogallery.data.data_source.cache.FileDao
import com.devcomentry.photogallery.data.data_source.mappers.FileEntityMapper
import com.devcomentry.photogallery.data.repository.FileRepositoryImpl
import com.devcomentry.photogallery.domain.repository.FileRepository
import com.devcomentry.photogallery.domain.use_case.*
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
    fun provideRepository(
        fileDao: FileDao,
        fileEntityMapper: FileEntityMapper
    ): FileRepository = FileRepositoryImpl(
        fileDao,
        fileEntityMapper
    )

    @Provides
    @Singleton
    fun provideUseCases(fileRepository: FileRepository): FileUseCases = FileUseCases(
        addFile = AddFile(fileRepository),
        updateFile = UpdateFile(fileRepository),
        removeFile = RemoveFile(fileRepository),
        getFileById = GetFileById(fileRepository),
        getFileByType = GetFileByType(fileRepository),
    )

}