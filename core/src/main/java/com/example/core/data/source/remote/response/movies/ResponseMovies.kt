package com.example.core.data.source.remote.response.movies

import com.google.gson.annotations.SerializedName

data class ResponseMovies(
    @SerializedName("id")
    val moviesId: String,

    @SerializedName("title")
    val moviesTitle: String,

    @SerializedName("genre_ids")
    val moviesGenre: List<Int>,

    @SerializedName("overview")
    val moviesOverview: String,

    @SerializedName("popularity")
    val moviesPopularity: Double,

    @SerializedName("vote_average")
    val moviesScore: String,

    @SerializedName("poster_path")
    val moviesImage: String?,

    @SerializedName("release_date")
    val moviesDate: String
)