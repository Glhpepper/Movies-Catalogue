package com.example.core.data.source.remote.response.shows.detail

import com.google.gson.annotations.SerializedName

data class SeasonsItem(

    @SerializedName("name")
    val name: String?,

    @SerializedName("poster_path")
    val posterPath: String?
)