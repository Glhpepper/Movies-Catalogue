package com.example.moviescatalogue.model.movies

data class Movies(
    val moviesId: String,
    val moviesTitle: String,
    val moviesGenre: List<Int>,
    val moviesOverview: String,
    val moviesScore: String,
    val moviesPopularity: Double,
    val moviesImage: String?,
    val moviesDate: String
)