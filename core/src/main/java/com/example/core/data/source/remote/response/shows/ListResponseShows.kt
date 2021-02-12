package com.example.core.data.source.remote.response.shows

import com.google.gson.annotations.SerializedName

data class ListResponseShows(
    @SerializedName("results")
    val results: List<ResponseShows>
)