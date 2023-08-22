package com.example.moviediscoveryapplication.repository

import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.model.MoviesListResponse

interface TheMovieDbRepository {
    suspend fun getPopularMovies(): Result<MoviesListResponse>
    suspend fun getGenresList(): Result<GenresListResponse>
    suspend fun getTopRatedMovies(): Result<MoviesListResponse>
    suspend fun getFeaturedMovies(): Result<MoviesListResponse>
}