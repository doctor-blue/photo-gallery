package com.devcomentry.photogallery.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devcomentry.photogallery.data.data_source.cache.AppDatabase
import com.devcomentry.photogallery.data.data_source.mappers.FileEntityMapper
import com.devcomentry.photogallery.data.repository.FileRepositoryImpl
import com.devcomentry.photogallery.domain.repository.FileRepository
import com.devcomentry.photogallery.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .enableMultiInstanceInvalidation()
                .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                .build()

            INSTANCE = instance
            instance
        }
    }

    @Provides
    @Singleton
    fun provideFileMapper(): FileEntityMapper = FileEntityMapper()


    @Provides
    @Singleton
    fun provideRepository(
        appDatabase: AppDatabase,
        fileEntityMapper: FileEntityMapper
    ): FileRepository = FileRepositoryImpl(
        appDatabase.fileDao,
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