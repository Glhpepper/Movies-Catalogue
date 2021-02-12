package com.example.moviescatalogue.model.detail.shows

import com.example.moviescatalogue.model.detail.GenreDetail

data class FavoriteShows(
    val id: Int?,
    val genres: List<GenreDetail>?,
    val popularity: Double?,
    val voteCount: Int?,
    val firstAirDate: String?,
    val overview: String?,
    val seasons: List<SeasonsItemDetail>?,
    val posterPath: String?,
    val voteAverage: Double?,
    val name: String?,
    val homepage: String?,
)