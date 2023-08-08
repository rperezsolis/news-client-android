package com.example.news_client_android.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news_client_android.data.datasources.local.dao.SavedArticleDao
import com.example.news_client_android.data.datasources.local.entities.SavedArticle

@Database(entities = [SavedArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedArticleDao(): SavedArticleDao

    companion object {
        const val name = "AppDatabase"
    }
}