package com.example.core.domain.model.detail.shows

import com.example.core.domain.model.detail.GenreDetailDomain

data class DetailShowsDomain(
    val id: Int?,
    val genreDomains: List<GenreDetailDomain>?,
    val popularity: Double?,
    val voteCount: Int?,
    val firstAirDate: String?,
    val overview: String?,
    val seasons: List<SeasonsItemDomain>?,
    val posterPath: String?,
    val voteAverage: Double?,
    val name: String?,
    val homepage: String?
)