package com.example.moviediscoveryapplication.usecase

import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.common.result.mapSuccess
import com.example.moviediscoveryapplication.model.GenreItem
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import javax.inject.Inject

class GetGenresListUseCase @Inject constructor(
    private val repository: TheMovieDbRepository
) {
    suspend fun invoke(): Result<ArrayList<GenreItem>> {
        return repository.getGenresList().mapSuccess { it.genres }
    }

}