package com.example.moviediscoveryapplication.mocks

import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.model.CarouselItem
import com.example.moviediscoveryapplication.model.ContentItem

val moviesListCarousel = listOf(
    CarouselItem("Movie 1", "Release", R.drawable.ic_profile),
    CarouselItem("Movie 2", "Release", R.drawable.ic_profile),
    CarouselItem("Movie 3", "Release", R.drawable.ic_profile)
)

val movieCategories = listOf(
    "Action", "Comedy", "Drama", "Sci-Fi", "Horror", "Adventure"
)

val moviesList = listOf(
    ContentItem("Spider-Man", "Action", R.drawable.ic_profile),
    ContentItem("Life of PI", "Action", R.drawable.ic_profile),
    ContentItem("Riverdale", "Action", R.drawable.ic_profile)
)