package com.example.moviediscoveryapplication.network

import com.example.moviediscoveryapplication.model.GenresListResponse
import com.example.moviediscoveryapplication.model.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MoviesListResponse

    @GET("genre/movie/list?language=en")
    suspend fun getGenresList(): GenresListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MoviesListResponse

    @GET("movie/now_playing")
    suspend fun getFeaturedMovies(
        @Query("page") page: Int
    ): MoviesListResponse
}