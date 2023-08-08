package com.example.news_client_android.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.news_client_android.domain.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticleDetailViewModel : ViewModel() {
    private lateinit var _currentArticle: MutableStateFlow<Article>
    val currentArticle: StateFlow<Article> get() = _currentArticle

    private var _loadingState = MutableStateFlow(ArticleDetailLoadingState.NotStarted)
    val loadingState: StateFlow<ArticleDetailLoadingState> = _loadingState

    fun setCurrentArticle(article: Article) {
        if(!::_currentArticle.isInitialized) {
            _currentArticle = MutableStateFlow(article)
        } else {
            _currentArticle.value = article
        }
    }

    fun setLoadingState(loadingState: ArticleDetailLoadingState) {
        _loadingState.value = loadingState
    }
}

enum class ArticleDetailLoadingState { NotStarted, InProgress, Finished }