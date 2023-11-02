package com.example.moviediscoveryapplication.di

import android.app.Application
import android.content.Context
import com.example.moviediscoveryapplication.network.ConnectivityInterceptor
import com.example.moviediscoveryapplication.network.LoggingInterceptor
import com.example.moviediscoveryapplication.network.ModifyUrlInterceptor
import com.example.moviediscoveryapplication.network.ApiClient
import com.example.moviediscoveryapplication.network.CacheInterceptor
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
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(context: Context): ConnectivityInterceptor {
        return ConnectivityInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideModifyUrlInterceptor(): ModifyUrlInterceptor {
        return ModifyUrlInterceptor()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(): CacheInterceptor {
        return CacheInterceptor()
    }

    @Provides
    @Singleton
    fun provideTheMovieDbService(apiClient: ApiClient): TheMovieDbService {
        return apiClient.retrofit.create(TheMovieDbService::class.java)
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