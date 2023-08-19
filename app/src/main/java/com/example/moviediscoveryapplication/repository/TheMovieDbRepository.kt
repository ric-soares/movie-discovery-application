package com.example.moviediscoveryapplication.repository

import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.model.MostPopularMoviesResponse
import com.example.moviediscoveryapplication.common.result.Result

interface TheMovieDbRepository {
    suspend fun getPopularMovies(): Result<MostPopularMoviesResponse>
    suspend fun getGenresList(): Result<GenresListResponse>
}