package com.example.moviediscoveryapplication.di

import com.example.moviediscoveryapplication.network.ApiClient
import com.example.moviediscoveryapplication.network.TheMovieDbService
import com.example.moviediscoveryapplication.repository.TheMovieDbRepository
import com.example.moviediscoveryapplication.repository.TheMovieDbRepositoryImpl
import com.example.moviediscoveryapplication.usecase.GetPopularMoviesUseCase
import com.example.moviediscoveryapplication.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    single<TheMovieDbService> { get<ApiClient>().getClient().create(TheMovieDbService::class.java) }
    single<TheMovieDbRepository> { TheMovieDbRepositoryImpl(get()) }
    single { GetPopularMoviesUseCase(get()) }
    viewModel { HomeScreenViewModel(get()) }
}