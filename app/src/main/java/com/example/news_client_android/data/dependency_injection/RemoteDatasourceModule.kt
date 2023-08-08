package com.example.news_client_android.data.dependency_injection

import com.example.news_client_android.data.datasources.remote.ArticleRemoteDatasource
import com.example.news_client_android.data.datasources.remote.ArticleRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDatasourceModule {
    @Binds
    abstract fun bindArticleRemoteDatasource(
        articleRemoteDatasourceImpl: ArticleRemoteDatasourceImpl
    ): ArticleRemoteDatasource
}