package com.example.moviediscoveryapplication.di

import com.example.moviediscoveryapplication.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeScreenViewModel() }
}