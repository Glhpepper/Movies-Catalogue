package com.example.moviescatalogue.model.detail.movies

import com.example.moviescatalogue.model.detail.GenreDetail

data class DetailMovies(
    val id: Int?,
    val title: String?,
    val genres: List<GenreDetail>?,
    val popularity: Double?,
    val voteCount: Int?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val homepage: String?,
    val productionCompanies: List<ProductionCompanyDetail>?
)