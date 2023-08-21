package com.example.moviediscoveryapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscoveryapplication.common.result.Result
import com.example.moviediscoveryapplication.model.GenreItem
import com.example.moviediscoveryapplication.model.MovieItem
import com.example.moviediscoveryapplication.usecase.GetGenresListUseCase
import com.example.moviediscoveryapplication.usecase.GetPopularMoviesUseCase
import com.example.moviediscoveryapplication.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getGenresListUseCase: GetGenresListUseCase,
    private val getTopRatedMoviesList: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _popularMoviesList: MutableLiveData<ArrayList<MovieItem>> = MutableLiveData()
    val popularMoviesList: LiveData<ArrayList<MovieItem>> = _popularMoviesList

    private val _isPopularMoviesListLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isPopularMoviesListLoading: LiveData<Boolean> = _isPopularMoviesListLoading

    private val _genresList: MutableLiveData<ArrayList<GenreItem>> = MutableLiveData()
    val genresList: LiveData<ArrayList<GenreItem>> = _genresList

    private val _topRatedMoviesList: MutableLiveData<ArrayList<MovieItem>> = MutableLiveData()
    val topRatedMoviesList: LiveData<ArrayList<MovieItem>> = _topRatedMoviesList

    fun loadPopularMoviesList() {
        viewModelScope.launch {
            _isPopularMoviesListLoading.value = true
            when (val result = getPopularMoviesUseCase.invoke()) {
                is Result.Success -> {
                    val moviesList = result.data
                    _popularMoviesList.value = moviesList
                }
                is Result.Failure -> {
                    val errorCode = result.errorCode
                    val errorMessage = result.throwable.message
                    Log.d(errorCode.toString(), errorMessage.toString())
                }
            }
            _isPopularMoviesListLoading.value = false
        }
    }

    fun loadGenresList() {
        viewModelScope.launch {
            when (val result = getGenresListUseCase.invoke()) {
                is Result.Success -> {
                    val genresList = result.data
                    _genresList.value = genresList
                }
                is Result.Failure -> {
                    val errorCode = result.errorCode
                    val errorMessage = result.throwable.message
                    Log.d(errorCode.toString(), errorMessage.toString())
                }
            }
        }
    }

    fun loadTopRatedMoviesList() {
        viewModelScope.launch {
            when (val result = getTopRatedMoviesList.invoke()) {
                is Result.Success -> {
                    val moviesList = result.data
                    _topRatedMoviesList.value = moviesList
                }
                is Result.Failure -> {
                    val errorCode = result.errorCode
                    val errorMessage = result.throwable.message
                    Log.d(errorCode.toString(), errorMessage.toString())
                }
            }
        }
    }

}