package com.example.news_client_android.data.datasources.local.dao

import androidx.room.*
import com.example.news_client_android.data.datasources.local.entities.SavedArticle

@Dao
interface SavedArticleDao {
    @Query("SELECT * FROM ${SavedArticle.tableName}")
    suspend fun getSavedArticles(): List<SavedArticle>

    @Query("SELECT * FROM ${SavedArticle.tableName} WHERE url = :url")
    suspend fun getSavedArticleByUrl(url: String): SavedArticle?

    @Insert(entity = SavedArticle::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedArticle(savedArticle: SavedArticle)

    @Query("DELETE FROM ${SavedArticle.tableName} WHERE url = :url")
    suspend fun deleteSavedArticleByUrl(url: String): Int
}