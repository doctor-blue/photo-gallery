package com.devcomentry.photogallery.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devcomentry.photogallery.data.data_source.cache.DummiesDatabase
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
    fun provideDatabase(application: Application):RoomDatabase = Room.databaseBuilder(
        application,
        DummiesDatabase::class.java,
        DummiesDatabase.DB_NAME
    ).build()

}