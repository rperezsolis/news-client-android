package com.example.news_client_android.domain.use_cases
import com.example.news_client_android.data.models.ArticleResponse
import com.example.news_client_android.data.models.Result
import com.example.news_client_android.data.repositories.ArticleRepository
import com.example.news_client_android.domain.models.Article
import javax.inject.Inject

interface GetTopHeadlinesUseCase {
    suspend operator fun invoke(countryCode: String): Result<List<Article>>
}

class GetTopHeadlinesUseCaseImpl @Inject constructor(private val articleRepository: ArticleRepository) : GetTopHeadlinesUseCase {
    override suspend operator fun invoke(countryCode: String): Result<List<Article>> {
        return when (val articleResult: Result<ArticleResponse> = articleRepository.getTopHeadlines(countryCode = countryCode)) {
            is Result.SuccessResult -> {
                val articles: List<Article> = articleResult.data.articles.filter { article ->
                    !article.description.isNullOrEmpty() && !article.content.isNullOrEmpty() && !article.url.isNullOrEmpty()
                }.map {
                    Article(
                        title = it.title,
                        description = it.description!!,
                        content = it.content!!,
                        source = it.source?.name,
                        author = it.author,
                        url = it.url!!,
                        urlToImage = it.urlToImage
                    )
                }
                Result.SuccessResult(data = articles)
            }
            is Result.ErrorResult -> Result.ErrorResult(exception = articleResult.exception)
        }
    }
}