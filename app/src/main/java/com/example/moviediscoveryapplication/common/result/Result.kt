package com.example.moviediscoveryapplication.common.result

sealed class Result<T> {
    data class Success<T>(val result: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable, val errorCode: Int? = null) : Result<T>()
//    object Loading : Result<Nothing>()
}