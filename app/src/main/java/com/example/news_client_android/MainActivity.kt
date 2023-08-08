package com.example.news_client_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.news_client_android.presentation.viewmodels.ArticleDetailViewModel
import com.example.news_client_android.presentation.viewmodels.ArticlesViewModel
import com.example.news_client_android.presentation.views.routes.ScreenRoute
import com.example.news_client_android.presentation.views.screens.article_detail_screen.ArticleDetailScreen
import com.example.news_client_android.presentation.views.screens.article_feed_screen.ArticleFeedScreen
import com.example.news_client_android.presentation.views.theme.News_client_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val articlesViewModel: ArticlesViewModel by viewModels()
    private val articleDetailViewModel: ArticleDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countryCode = Locale.current.region
        articlesViewModel.getLatestArticles(countryCode = countryCode)

        setContent {
            News_client_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.ArticleFeed.route
                    ) {
                        composable(ScreenRoute.ArticleFeed.route) {
                            ArticleFeedScreen(
                                articlesViewModel = articlesViewModel,
                                articleDetailViewModel = articleDetailViewModel,
                                navigateToArticleDetail = {
                                    navController.navigate(ScreenRoute.ArticleDetail.route)
                                }
                            )
                        }
                        composable(ScreenRoute.ArticleDetail.route) {
                            ArticleDetailScreen(
                                articleDetailViewModel = articleDetailViewModel,
                                navigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}