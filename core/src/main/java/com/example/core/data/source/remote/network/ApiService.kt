package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.movies.ListResponseMovies
import com.example.core.data.source.remote.response.movies.detail.ResponseDetailMovies
import com.example.core.data.source.remote.response.shows.ListResponseShows
import com.example.core.data.source.remote.response.shows.detail.ResponseDetailShows
import com.example.core.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovie(@QueryMap query: Map<String, String>): ListResponseMovies

    @GET("search/movie")
    suspend fun searchMovie(@QueryMap query: Map<String, String>): ListResponseMovies

    @GET("discover/tv")
    suspend fun getShows(@QueryMap query: Map<String, String>): ListResponseShows

    @GET("search/tv")
    suspend fun searchShows(@QueryMap query: Map<String, String>): ListResponseShows

    @GET("movie/{id}?api_key=${API_KEY}")
    suspend fun getDetailMovies(@Path("id") id: String): ResponseDetailMovies

    @GET("tv/{id}?api_key=${API_KEY}")
    suspend fun getDetailTvShows(@Path("id") id: String): ResponseDetailShows
}