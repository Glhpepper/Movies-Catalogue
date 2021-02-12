package com.example.core.data.source.remote.response.shows.detail

import com.example.core.data.source.remote.response.Genre
import com.google.gson.annotations.SerializedName

data class ResponseDetailShows(
    @SerializedName("id")
    val id: Int,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("seasons")
    val seasons: List<SeasonsItem>,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("name")
    val name: String,

    @SerializedName("homepage")
    val homepage: String,
)