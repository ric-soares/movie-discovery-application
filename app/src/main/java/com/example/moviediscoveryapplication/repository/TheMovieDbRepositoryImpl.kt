package com.example.moviediscoveryapplication.repository

import com.example.moviediscoveryapplication.model.MostPopularMoviesResponse
import com.example.moviediscoveryapplication.network.TheMovieDbService

class TheMovieDbRepositoryImpl(
    private val service: TheMovieDbService
) : TheMovieDbRepository {
    override suspend fun getPopularMovies(): MostPopularMoviesResponse {
        return service.getPopularMovies(1)
    }
}