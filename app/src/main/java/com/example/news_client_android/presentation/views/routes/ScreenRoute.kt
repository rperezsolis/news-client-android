package com.example.news_client_android.presentation.views.routes

sealed class ScreenRoute(val route: String) {
    object ArticleFeed : ScreenRoute(route = "articleFeed")
    object ArticleDetail : ScreenRoute(route = "articleDetail")
}
