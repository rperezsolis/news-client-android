package com.example.news_client_android.presentation.views.screens.article_feed_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.news_client_android.data.datasources.local.SavedArticleLocalDatasourceImpl
import com.example.news_client_android.data.datasources.local.database.AppDatabase
import com.example.news_client_android.data.datasources.remote.ArticleRemoteDatasourceImpl
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApiImpl
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApiService
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesService
import com.example.news_client_android.data.repositories.ArticleRepositoryImpl
import com.example.news_client_android.domain.models.Article
import com.example.news_client_android.domain.use_cases.GetTopHeadlinesUseCaseImpl
import com.example.news_client_android.presentation.viewmodels.ArticleDetailViewModel
import com.example.news_client_android.presentation.viewmodels.ArticlesViewModel
import com.example.news_client_android.presentation.views.routes.ScreenRoute
import com.example.news_client_android.presentation.views.theme.News_client_androidTheme
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleFeedScreen(
    articlesViewModel: ArticlesViewModel,
    articleDetailViewModel: ArticleDetailViewModel,
    navigateToArticleDetail: () -> Unit
) {
    val countryCode = Locale.current.region
    val isRefreshing by remember { mutableStateOf(false) }
    val refresh: () -> Unit = {
        articlesViewModel.getLatestArticles(countryCode = countryCode)
    }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, refresh)
    val articles: List<Article> by articlesViewModel.articles.collectAsStateWithLifecycle()
    val exception: Exception? by articlesViewModel.exception.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "News Client")
            })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            if (articles.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles) { article ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    articleDetailViewModel.setCurrentArticle(article = article)
                                    navigateToArticleDetail()
                                },
                            text = { Text(text = article.title) },
                            secondaryText = { Text(text = article.description) },
                            icon = {
                                AsyncImage(
                                    model = article.urlToImage,
                                    contentDescription = "Descriptive picture",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .aspectRatio(1F),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        )
                        Divider(modifier = Modifier.padding(top = 8.dp))
                    }
                }
            } else {
                if (exception != null) {
                    Text(text = "An error occurred \n${exception?.localizedMessage}")
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    News_client_androidTheme {
        ArticleFeedScreen(
            ArticlesViewModel(GetTopHeadlinesUseCaseImpl(
                ArticleRepositoryImpl(
                    ArticleRemoteDatasourceImpl(
                        ArticlesApiImpl(ArticlesService.retrofitService)
                    ),
                    SavedArticleLocalDatasourceImpl(
                        Room.databaseBuilder(
                            context = LocalContext.current,
                            klass = AppDatabase::class.java,
                            name = AppDatabase.name
                        ).build().savedArticleDao()
                    )
                )
            )),
            ArticleDetailViewModel()
        ) { navController.navigate(ScreenRoute.ArticleDetail.route) }
    }
}