package com.example.logintest.di

import android.content.Context
import androidx.room.Room
import com.example.logintest.database.AppDatabase
import com.example.logintest.database.LoginDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "app-database"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoginDao(appDatabase: AppDatabase): LoginDao {
        return appDatabase.getLoginDao()
    }
}