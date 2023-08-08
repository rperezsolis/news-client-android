package com.example.news_client_android.data.datasources.local

import com.example.news_client_android.data.datasources.local.dao.SavedArticleDao
import com.example.news_client_android.data.datasources.local.entities.SavedArticle
import javax.inject.Inject

interface SavedArticleLocalDatasource {
    suspend fun getSavedArticles(): List<SavedArticle>
    suspend fun getSavedArticleByUrl(url: String): SavedArticle?
    suspend fun saveArticle(title: String, description: String, content: String, url: String,
                            author: String?, source: String?)
    suspend fun unSaveArticle(url: String): Int
}

class SavedArticleLocalDatasourceImpl @Inject constructor(private val savedArticleDao: SavedArticleDao) : SavedArticleLocalDatasource {
    override suspend fun getSavedArticles(): List<SavedArticle> {
        return savedArticleDao.getSavedArticles()
    }

    override suspend fun getSavedArticleByUrl(url: String): SavedArticle? {
        return savedArticleDao.getSavedArticleByUrl(url = url)
    }

    override suspend fun saveArticle(
        title: String,
        description: String,
        content: String,
        url: String,
        author: String?,
        source: String?
    ) {
        val savedArticle = SavedArticle(title = title, description = description, content = content,
            url = url, author = author, source = source)
        return savedArticleDao.insertSavedArticle(savedArticle = savedArticle)
    }

    override suspend fun unSaveArticle(url: String): Int {
        return savedArticleDao.deleteSavedArticleByUrl(url = url)
    }
}