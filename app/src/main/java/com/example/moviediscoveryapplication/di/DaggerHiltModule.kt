package com.example.moviediscoveryapplication.di

import com.example.moviediscoveryapplication.network.ApiClient
import com.example.moviediscoveryapplication.network.TheMovieDbService
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import com.example.moviediscoveryapplication.repository.TheMovieDbRepositoryImpl
import com.example.moviediscoveryapplication.usecase.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient()
    }

    @Provides
    @Singleton
    fun provideTheMovieDbService(apiClient: ApiClient): TheMovieDbService {
        return apiClient.getClient().create(TheMovieDbService::class.java)
    }

    @Provides
    @Singleton
    fun provideTheMovieDbRepository(service: TheMovieDbService): TheMovieDbRepository {
        return TheMovieDbRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: TheMovieDbRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }
}