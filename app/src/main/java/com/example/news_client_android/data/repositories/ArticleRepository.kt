package com.example.news_client_android.data.repositories
import com.example.news_client_android.data.datasources.local.SavedArticleLocalDatasource
import com.example.news_client_android.data.datasources.local.entities.SavedArticle
import com.example.news_client_android.data.datasources.remote.ArticleRemoteDatasource
import com.example.news_client_android.data.models.ArticleResponse
import com.example.news_client_android.data.models.Result
import javax.inject.Inject

interface ArticleRepository {
    suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse>
    suspend fun getSavedArticles(): List<SavedArticle>
    suspend fun getSavedArticleByUrl(url: String): SavedArticle?
    suspend fun saveArticle(title: String, description: String, content: String, url: String,
                             author: String?, source: String?)
    suspend fun unSaveArticle(url: String): Int
}

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDatasource: ArticleRemoteDatasource,
    private val savedArticleLocalDatasource: SavedArticleLocalDatasource
    ) : ArticleRepository {
    override suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse> {
        return articleRemoteDatasource.getTopHeadlines(countryCode = countryCode)
    }

    override suspend fun getSavedArticles(): List<SavedArticle> {
        return savedArticleLocalDatasource.getSavedArticles()
    }

    override suspend fun getSavedArticleByUrl(url: String): SavedArticle? {
        return savedArticleLocalDatasource.getSavedArticleByUrl(url = url)
    }

    override suspend fun saveArticle(
        title: String,
        description: String,
        content: String,
        url: String,
        author: String?,
        source: String?
    ) {
        return savedArticleLocalDatasource.saveArticle(title = title, description = description, content = content,
            url = url, author = author, source = source)
    }

    override suspend fun unSaveArticle(url: String): Int {
        return savedArticleLocalDatasource.unSaveArticle(url = url)
    }
}