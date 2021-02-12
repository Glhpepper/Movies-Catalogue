package com.example.core.data.source.remote.response.movies.detail


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?
)