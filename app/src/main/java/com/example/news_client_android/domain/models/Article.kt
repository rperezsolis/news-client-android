package com.example.news_client_android.domain.models

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val source: String?,
    val author: String?,
    val url: String,
    val urlToImage: String?
)
