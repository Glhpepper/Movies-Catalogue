package com.example.core.data.source.remote.response.movies

import com.google.gson.annotations.SerializedName

data class ListResponseMovies(
    @SerializedName("results")
    val results: List<ResponseMovies>
)