package com.example.news_client_android.data.dependency_injection

import com.example.news_client_android.data.datasources.local.SavedArticleLocalDatasource
import com.example.news_client_android.data.datasources.local.SavedArticleLocalDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalDatasourceModule {
    @Binds
    abstract fun bindSavedArticleLocalDatasource(
        savedArticleLocalDatasourceImpl: SavedArticleLocalDatasourceImpl
    ): SavedArticleLocalDatasource
}