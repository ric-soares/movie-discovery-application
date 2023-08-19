package com.example.moviediscoveryapplication.usecase

import com.example.moviediscoveryapplication.common.exceptions.NetworkExceptions
import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.model.GenreItem
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import retrofit2.HttpException
import javax.inject.Inject

class GetGenresListUseCase @Inject constructor(
    private val repository: TheMovieDbRepository
) {
    suspend fun invoke(): Result<ArrayList<GenreItem>> = try {
        Result.Success(repository.getGenresList().genres)
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