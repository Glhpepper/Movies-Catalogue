package com.example.core.data.source.remote.response.shows

import com.google.gson.annotations.SerializedName

data class ResponseShows(
    @SerializedName("id")
    val showsId: String,

    @SerializedName("name")
    val showsTitle: String,

    @SerializedName("genre_ids")
    val showsGenre: List<Int>,

    @SerializedName("overview")
    val showsOverview: String,

    @SerializedName("popularity")
    val showsPopularity: Double,

    @SerializedName("vote_average")
    val showsScore: String,

    @SerializedName("poster_path")
    val showsImage: String?,

    @SerializedName("first_air_date")
    val showsDate: String
)