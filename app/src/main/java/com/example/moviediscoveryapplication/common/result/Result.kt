package com.example.moviediscoveryapplication.common.result

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val throwable: Throwable, val errorCode: Int? = null) : Result<Nothing>()
}

inline fun <T, R> Result<T>.mapSuccess(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> this
    }
}