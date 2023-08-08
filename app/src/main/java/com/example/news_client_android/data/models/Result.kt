package com.example.news_client_android.data.models

sealed class Result<T> {
    class SuccessResult<T>(val data: T) : Result<T>()
    class ErrorResult<T>(val exception: Exception) : Result<T>()
}
