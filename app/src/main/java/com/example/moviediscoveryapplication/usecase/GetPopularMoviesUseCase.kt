package com.example.moviediscoveryapplication.usecase

import com.example.moviediscoveryapplication.common.exceptions.NetworkExceptions
import com.example.moviediscoveryapplication.model.MostPopularMoviesItem
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import com.example.moviediscoveryapplication.common.result.Result
import retrofit2.HttpException
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: TheMovieDbRepository
) {
    suspend fun invoke(): Result<ArrayList<MostPopularMoviesItem>> = try {
        Result.Success(repository.getPopularMovies().results)
    } catch (error: HttpException) {
        Result.Failure(
            when (error.code()) {
                400 -> NetworkExceptions.BadRequestException()
                401 -> NetworkExceptions.UnauthorizedException()
                403 -> NetworkExceptions.ForbiddenException()
                404 -> NetworkExceptions.NotFoundException()
                else -> NetworkExceptions.HttpErrorException(error.code())
            }
        )
    }
}