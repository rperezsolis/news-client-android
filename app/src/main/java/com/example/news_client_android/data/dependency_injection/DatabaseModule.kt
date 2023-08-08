package com.example.news_client_android.data.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.news_client_android.data.datasources.local.dao.SavedArticleDao
import com.example.news_client_android.data.datasources.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = AppDatabase.name
        ).build()
    }

    @Provides
    fun provideSavedArticleDao(appDatabase: AppDatabase): SavedArticleDao {
        return appDatabase.savedArticleDao()
    }
}