package com.example.moviediscoveryapplication.network

import com.example.moviediscoveryapplication.model.MostPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MostPopularMoviesResponse
}