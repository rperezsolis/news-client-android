package com.example.news_client_android.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_client_android.data.models.Result
import com.example.news_client_android.domain.models.Article
import com.example.news_client_android.domain.use_cases.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _exception = MutableStateFlow<Exception?>(null)
    val exception: StateFlow<Exception?> = _exception

    fun getLatestArticles(countryCode: String) {
        viewModelScope.launch {
            when (val articleResult = getTopHeadlinesUseCase(countryCode = countryCode)) {
                is Result.SuccessResult -> _articles.value = articleResult.data
                is Result.ErrorResult -> _exception.value = articleResult.exception
            }
        }
    }
}