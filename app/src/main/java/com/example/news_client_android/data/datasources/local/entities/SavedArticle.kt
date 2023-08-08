package com.example.news_client_android.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SavedArticle.tableName)
data class SavedArticle(
    val title: String,
    val description: String,
    val content: String,
    val source: String?,
    val author: String?,
    @PrimaryKey val url: String
) {
    companion object {
        const val tableName = "saved_article"
    }
}