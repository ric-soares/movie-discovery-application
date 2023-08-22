package com.example.moviediscoveryapplication.usecase

import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.common.result.mapSuccess
import com.example.moviediscoveryapplication.model.MovieItem
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import javax.inject.Inject

class GetFeaturedMoviesUseCase @Inject constructor(
    private val repository: TheMovieDbRepository
) {
    suspend fun invoke(): Result<ArrayList<MovieItem>> {
        return repository.getFeaturedMovies().mapSuccess { it.results }
    }
}