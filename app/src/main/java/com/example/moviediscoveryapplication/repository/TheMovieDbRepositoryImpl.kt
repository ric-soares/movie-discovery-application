package com.example.moviediscoveryapplication.repository

import com.example.moviediscoveryapplication.common.exceptions.NetworkExceptions
import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.network.TheMovieDbService
import javax.inject.Inject
import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.model.MoviesListResponse
import retrofit2.HttpException

class TheMovieDbRepositoryImpl @Inject constructor(
    private val service: TheMovieDbService
) : TheMovieDbRepository {

    override suspend fun getPopularMovies(): Result<MoviesListResponse> {
        return try {
            val response = service.getPopularMovies(1)
            Result.Success(response)
        } catch (error: HttpException) {
            Result.Failure(NetworkExceptions.fromHttpException(error))
        } catch (error: Exception) {
            Result.Failure(Exception("cause: ${error.cause} -> message: ${error.message}"))
        }
    }

    override suspend fun getGenresList(): Result<GenresListResponse> {
        return try {
            val response = service.getGenresList()
            Result.Success(response)
        } catch (error: HttpException) {
            Result.Failure(NetworkExceptions.fromHttpException(error))
        } catch (error: Exception) {
            Result.Failure(Exception("cause: ${error.cause} -> message: ${error.message}"))
        }
    }

    override suspend fun getTopRatedMovies(): Result<MoviesListResponse> {
        return try {
            val response = service.getTopRatedMovies(1)
            Result.Success(response)
        } catch (error: HttpException) {
            Result.Failure(NetworkExceptions.fromHttpException(error))
        } catch (error: Exception) {
            Result.Failure(Exception("cause: ${error.cause} -> message: ${error.message}"))
        }
    }

    override suspend fun getFeaturedMovies(): Result<MoviesListResponse> {
        return try {
            val response = service.getFeaturedMovies(1)
            Result.Success(response)
        } catch (error: HttpException) {
            Result.Failure(NetworkExceptions.fromHttpException(error))
        } catch (error: Exception) {
            Result.Failure(Exception("cause: ${error.cause} -> message: ${error.message}"))
        }
    }

}