package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.domain.model.GenreAndYearDomain
import com.example.core.domain.model.detail.movies.DetailMoviesDomain
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import com.example.core.domain.model.detail.shows.DetailShowsDomain
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import com.example.core.domain.model.movies.MoviesDomain
import com.example.core.domain.model.shows.ShowsDomain
import kotlinx.coroutines.flow.Flow

interface IMainRepository {

    fun getMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>>

    fun getShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>>

    suspend fun saveGenreAndYearMovies(
        genre: String, genreId: Int, year: String, yearId: Int
    )

    suspend fun saveGenreAndYearShows(
        genre: String, genreId: Int, year: String, yearId: Int
    )

    fun readGenreAndYearMovies(): Flow<GenreAndYearDomain>

    fun readGenreAndYearShows(): Flow<GenreAndYearDomain>

    fun getDetailMovies(id: Int): Flow<Resource<DetailMoviesDomain>>

    fun getDetailShows(id: Int): Flow<Resource<DetailShowsDomain>>

    suspend fun insertFavoriteMovies(favoriteMovies: FavoriteMoviesEntity)

    suspend fun checkFavoriteMovies(id: Int): Boolean

    fun getFavoriteMovies(): Flow<Resource<List<FavoriteMoviesDomain>>>

    fun searchFavoriteMovies(query: String): Flow<List<FavoriteMoviesDomain>>

    suspend fun deleteFavoriteMoviesById(id: Int)

    suspend fun deleteFavoriteMovies(favoriteMovies: FavoriteMoviesEntity)

    suspend fun deleteAllFavoriteMovies()

    suspend fun insertFavoriteShows(favoriteShows: FavoriteShowsEntity)

    suspend fun checkFavoriteShows(id: Int): Boolean

    fun getFavoriteShows(): Flow<Resource<List<FavoriteShowsDomain>>>

    fun searchFavoriteShows(query: String): Flow<List<FavoriteShowsDomain>>

    suspend fun deleteFavoriteShowsById(id: Int)

    suspend fun deleteFavoriteShows(favoriteShows: FavoriteShowsEntity)

    suspend fun deleteAllFavoriteShows()

    fun getUpcomingMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>>

    fun getUpcomingShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>>

    fun searchMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>>

    fun searchShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>>
}