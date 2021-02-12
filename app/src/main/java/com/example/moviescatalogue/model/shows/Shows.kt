package com.example.moviescatalogue.model.shows

data class Shows(
    val showsId: String,
    val showsTitle: String,
    val showsGenre: List<Int>,
    val showsOverview: String,
    val showsScore: String,
    val showsPopularity: Double,
    val showsImage: String?,
    val showsDate: String
)