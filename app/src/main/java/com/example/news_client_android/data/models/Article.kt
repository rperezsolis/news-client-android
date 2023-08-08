package com.example.news_client_android.data.models

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val source: Source?,
    val author: String?,
    val url: String?,
    val urlToImage: String?)
