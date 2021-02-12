package com.example.core.domain.usecase

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
import com.example.core.domain.repository.IMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryInteractor @Inject constructor(private val mainRepository: IMainRepository) :
    MainRepositoryUseCase {

    override fun getMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return mainRepository.getMovies(query)
    }

    override fun getShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return mainRepository.getShows(query)
    }

    override fun getDetailMovies(id: Int): Flow<Resource<DetailMoviesDomain>> {
        return mainRepository.getDetailMovies(id)
    }

    override fun getDetailShows(id: Int): Flow<Resource<DetailShowsDomain>> {
        return mainRepository.getDetailShows(id)
    }

    override suspend fun saveGenreAndYearMovies(
        genre: String, genreId: Int, year: String, yearId: Int
    ) {
        mainRepository.saveGenreAndYearMovies(genre, genreId, year, yearId)
    }

    override suspend fun saveGenreAndYearShows(
        genre: String, genreId: Int, year: String, yearId: Int
    ) {
        mainRepository.saveGenreAndYearShows(genre, genreId, year, yearId)
    }

    override fun readGenreAndYearMovies(): Flow<GenreAndYearDomain> {
        return mainRepository.readGenreAndYearMovies()
    }

    override fun readGenreAndYearShows(): Flow<GenreAndYearDomain> {
        return mainRepository.readGenreAndYearShows()
    }

    override suspend fun insertFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        mainRepository.insertFavoriteMovies(favoriteMovies)
    }

    override suspend fun checkFavoriteMovies(id: Int): Boolean {
        return mainRepository.checkFavoriteMovies(id)
    }

    override fun getFavoriteMovies(): Flow<Resource<List<FavoriteMoviesDomain>>> {
        return mainRepository.getFavoriteMovies()
    }

    override fun searchFavoriteMovies(query: String): Flow<List<FavoriteMoviesDomain>> {
        return mainRepository.searchFavoriteMovies(query)
    }

    override suspend fun deleteFavoriteMoviesById(id: Int) {
        mainRepository.deleteFavoriteMoviesById(id)
    }

    override suspend fun deleteFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        mainRepository.deleteFavoriteMovies(favoriteMovies)
    }

    override suspend fun deleteAllFavoriteMovies() {
        mainRepository.deleteAllFavoriteMovies()
    }

    override suspend fun insertFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        mainRepository.insertFavoriteShows(favoriteShows)
    }

    override suspend fun checkFavoriteShows(id: Int): Boolean {
        return mainRepository.checkFavoriteShows(id)
    }

    override fun getFavoriteShows(): Flow<Resource<List<FavoriteShowsDomain>>> {
        return mainRepository.getFavoriteShows()
    }

    override fun searchFavoriteShows(query: String): Flow<List<FavoriteShowsDomain>> {
        return mainRepository.searchFavoriteShows(query)
    }

    override suspend fun deleteFavoriteShowsById(id: Int) {
        mainRepository.deleteFavoriteShowsById(id)
    }

    override suspend fun deleteFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        mainRepository.deleteFavoriteShows(favoriteShows)
    }

    override suspend fun deleteAllFavoriteShows() {
        mainRepository.deleteAllFavoriteShows()
    }

    override fun getUpcomingMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return mainRepository.getUpcomingMovies(query)
    }

    override fun getUpcomingShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return mainRepository.getUpcomingShows(query)
    }

    override fun searchMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return mainRepository.searchMovies(query)
    }

    override fun searchShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return mainRepository.searchShows(query)
    }
}