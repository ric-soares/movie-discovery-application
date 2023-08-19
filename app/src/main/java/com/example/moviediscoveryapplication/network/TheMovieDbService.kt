package com.example.moviediscoveryapplication.network

import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.model.MostPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MostPopularMoviesResponse

    @GET("genre/movie/list?language=en")
    suspend fun getGenresList(): GenresListResponse
}