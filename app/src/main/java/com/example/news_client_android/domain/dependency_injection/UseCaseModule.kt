package com.example.news_client_android.domain.dependency_injection

import com.example.news_client_android.domain.use_cases.GetTopHeadlinesUseCase
import com.example.news_client_android.domain.use_cases.GetTopHeadlinesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetTopHeadlineUseCase(
        getTopHeadlinesUseCaseImpl: GetTopHeadlinesUseCaseImpl
    ): GetTopHeadlinesUseCase
}