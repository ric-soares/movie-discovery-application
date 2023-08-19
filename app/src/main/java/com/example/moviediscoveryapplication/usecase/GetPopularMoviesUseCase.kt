package com.example.moviediscoveryapplication.usecase

import com.example.moviediscoveryapplication.model.MostPopularMoviesItem
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.common.result.mapSuccess
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: TheMovieDbRepository
) {
    suspend fun invoke(): Result<ArrayList<MostPopularMoviesItem>> {
        return repository.getPopularMovies().mapSuccess { it.results }
    }

}