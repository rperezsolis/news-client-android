package com.example.news_client_android.data.models

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
