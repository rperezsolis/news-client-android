package com.example.news_client_android.data.datasources.remote.network_api

import com.example.news_client_android.data.datasources.remote.baseUrl
import com.example.news_client_android.data.datasources.remote.newsApiKey
import com.example.news_client_android.data.datasources.remote.topHeadlinesEndpoint
import com.example.news_client_android.data.models.ArticleResponse
import com.example.news_client_android.data.models.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(baseUrl)
    .build()

interface ArticlesApiService {
    @Headers("Authorization:$newsApiKey")
    @GET(topHeadlinesEndpoint)
    suspend fun getTopHeadlines(@Query("country") countryCode: String): ArticleResponse
}

object ArticlesService {
    val retrofitService: ArticlesApiService by lazy {
        retrofit.create(ArticlesApiService::class.java)
    }
}

interface ArticlesApi {
    suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse>
}

class ArticlesApiImpl @Inject constructor(private val articleApiService: ArticlesApiService) : ArticlesApi {
    override suspend fun getTopHeadlines(countryCode: String): Result<ArticleResponse> {
        return try {
            val response: ArticleResponse = articleApiService.getTopHeadlines(countryCode = countryCode)
            Result.SuccessResult(data = response)
        } catch (exception: Exception) {
            Result.ErrorResult(exception = exception)
        }
    }
}