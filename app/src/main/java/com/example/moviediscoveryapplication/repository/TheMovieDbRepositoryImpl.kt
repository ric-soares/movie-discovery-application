package com.example.moviediscoveryapplication.repository

import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.model.MostPopularMoviesResponse
import com.example.moviediscoveryapplication.network.TheMovieDbService
import javax.inject.Inject

class TheMovieDbRepositoryImpl @Inject constructor(
    private val service: TheMovieDbService
) : TheMovieDbRepository {
    override suspend fun getPopularMovies(): MostPopularMoviesResponse {
        return service.getPopularMovies(1)
    }

    override suspend fun getGenresList(): GenresListResponse {
        return service.getGenresList()
    }
}