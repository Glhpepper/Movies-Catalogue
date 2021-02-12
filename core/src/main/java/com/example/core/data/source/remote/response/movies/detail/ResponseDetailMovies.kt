package com.example.core.data.source.remote.response.movies.detail


import com.example.core.data.source.remote.response.Genre
import com.google.gson.annotations.SerializedName

data class ResponseDetailMovies(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>
)