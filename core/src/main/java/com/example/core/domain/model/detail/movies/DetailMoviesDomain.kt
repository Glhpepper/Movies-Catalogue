package com.example.core.domain.model.detail.movies

import com.example.core.domain.model.detail.GenreDetailDomain

data class DetailMoviesDomain(
    val id: Int?,
    val title: String?,
    val genreDomains: List<GenreDetailDomain>?,
    val popularity: Double?,
    val voteCount: Int?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val homepage: String?,
    val productionCompanies: List<ProductionCompanyDomain>?
)