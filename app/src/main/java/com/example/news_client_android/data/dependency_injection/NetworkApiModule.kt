package com.example.news_client_android.data.dependency_injection

import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApi
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApiImpl
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesApiService
import com.example.news_client_android.data.datasources.remote.network_api.ArticlesService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NetworkApiModule {
    @Binds
    abstract fun bindArticlesApi(
        analyticsServiceImpl: ArticlesApiImpl
    ): ArticlesApi
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiServiceModule {
    @Provides
    fun provideArticlesApiService(): ArticlesApiService {
        return ArticlesService.retrofitService
    }
}
