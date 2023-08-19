package com.example.moviediscoveryapplication.model

data class GenresListResponse(
    val genres: ArrayList<GenreItem>
)

data class GenreItem(
    val id: Int,
    val name: String
)