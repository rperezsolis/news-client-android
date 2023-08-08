package com.example.news_client_android.presentation.views.screens.article_detail_screen

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.news_client_android.domain.models.Article
import com.example.news_client_android.presentation.viewmodels.ArticleDetailLoadingState
import com.example.news_client_android.presentation.viewmodels.ArticleDetailViewModel

@Composable
fun ArticleDetailScreen(
    articleDetailViewModel: ArticleDetailViewModel,
    navigateBack: () -> Unit
) {
    val article by articleDetailViewModel.currentArticle.collectAsStateWithLifecycle()
    val loadingState by articleDetailViewModel.loadingState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "News Client")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            articleDetailViewModel.setLoadingState(ArticleDetailLoadingState.NotStarted)
                            navigateBack()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            ArticleWebView(
                article = article,
                onPageStarted = {
                    if (loadingState == ArticleDetailLoadingState.NotStarted) {
                        articleDetailViewModel.setLoadingState(ArticleDetailLoadingState.InProgress)
                    }
                },
                onPageFinished = {
                    if (loadingState == ArticleDetailLoadingState.InProgress) {
                        articleDetailViewModel.setLoadingState(ArticleDetailLoadingState.Finished)
                    }
                },
                modifier = Modifier.padding(padding)
            )
            if (loadingState != ArticleDetailLoadingState.Finished) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ArticleWebView(
    article: Article,
    onPageStarted: () -> Unit,
    onPageFinished: () -> Unit,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onPageStarted()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onPageFinished()
                    }
                }
            }
        },
        modifier = modifier,
        update = { webView ->
            webView.loadUrl(article.url)
        }
    )
}