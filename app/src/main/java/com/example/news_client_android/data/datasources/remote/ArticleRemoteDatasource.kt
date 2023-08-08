package com.example.news_client_android.data.datasources.remote

import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApi
import com.example.news_client_android.data.models.ArticleResponse
import com.example.news_client_android.data.models.Result
import javax.inject.Inject

interface ArticleRemoteDatasource {
    suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse>
}

class ArticleRemoteDatasourceImpl @Inject constructor(private val articlesNetworkApi: ArticlesApi) : ArticleRemoteDatasource {
    override suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse> {
        return articlesNetworkApi.getTopHeadlines(countryCode = countryCode)
    }
}