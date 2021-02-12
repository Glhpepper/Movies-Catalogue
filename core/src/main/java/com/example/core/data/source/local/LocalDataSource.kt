package com.example.core.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowsEntity
import com.example.core.data.source.local.entity.detail.movies.DetailMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.shows.DetailShowsEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingMoviesEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingShowsEntity
import com.example.core.data.source.local.room.MoviesDao
import com.example.core.data.source.local.room.ShowsDao
import com.example.core.domain.model.GenreAndYearDomain
import com.example.core.utils.Constants.Companion.DATA_STORE_NAME
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_MOVIES
import com.example.core.utils.Constants.Companion.DEFAULT_GENRE_SHOWS
import com.example.core.utils.Constants.Companion.DEFAULT_PREFERENCE_ID
import com.example.core.utils.Constants.Companion.DEFAULT_YEAR
import com.example.core.utils.Constants.Companion.PREFERENCE_GENRE_MOVIES
import com.example.core.utils.Constants.Companion.PREFERENCE_GENRE_MOVIES_ID
import com.example.core.utils.Constants.Companion.PREFERENCE_GENRE_SHOWS
import com.example.core.utils.Constants.Companion.PREFERENCE_GENRE_SHOWS_ID
import com.example.core.utils.Constants.Companion.PREFERENCE_YEAR_MOVIES
import com.example.core.utils.Constants.Companion.PREFERENCE_YEAR_MOVIES_ID
import com.example.core.utils.Constants.Companion.PREFERENCE_YEAR_SHOWS
import com.example.core.utils.Constants.Companion.PREFERENCE_YEAR_SHOWS_ID
import com.example.core.utils.Constants.Companion.QUERY_GENRE
import com.example.core.utils.Constants.Companion.QUERY_YEAR_MOVIES
import com.example.core.utils.Constants.Companion.QUERY_YEAR_SHOWS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moviesDao: MoviesDao,
    private val showsDao: ShowsDao,
) {
    private object PreferenceKeys {
        val selectedGenreMovies = stringPreferencesKey(PREFERENCE_GENRE_MOVIES)
        val selectedGenreMoviesId = intPreferencesKey(PREFERENCE_GENRE_MOVIES_ID)
        val selectedYearMovies = stringPreferencesKey(PREFERENCE_YEAR_MOVIES)
        val selectedYearMoviesId = intPreferencesKey(PREFERENCE_YEAR_MOVIES_ID)
        val selectedGenreShows = stringPreferencesKey(PREFERENCE_GENRE_SHOWS)
        val selectedGenreShowsId = intPreferencesKey(PREFERENCE_GENRE_SHOWS_ID)
        val selectedYearShows = stringPreferencesKey(PREFERENCE_YEAR_SHOWS)
        val selectedYearShowsId = intPreferencesKey(PREFERENCE_YEAR_SHOWS_ID)
    }

    private val dataStore = context.createDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun saveGenreAndYearMovies(genre: String, genreId: Int, year: String, yearId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedGenreMovies] = genre
            preferences[PreferenceKeys.selectedGenreMoviesId] = genreId
            preferences[PreferenceKeys.selectedYearMovies] = year
            preferences[PreferenceKeys.selectedYearMoviesId] = yearId
        }
    }

    suspend fun saveGenreAndYearShows(genre: String, genreId: Int, year: String, yearId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedGenreShows] = genre
            preferences[PreferenceKeys.selectedGenreShowsId] = genreId
            preferences[PreferenceKeys.selectedYearShows] = year
            preferences[PreferenceKeys.selectedYearShowsId] = yearId
        }
    }

    fun readGenreAndYearMovies(): Flow<GenreAndYearDomain> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedGenre =
                    preferences[PreferenceKeys.selectedGenreMovies] ?: DEFAULT_GENRE_MOVIES
                val selectedGenreId =
                    preferences[PreferenceKeys.selectedGenreMoviesId] ?: DEFAULT_PREFERENCE_ID
                val selectedYear = preferences[PreferenceKeys.selectedYearMovies] ?: DEFAULT_YEAR
                val selectedYeaId =
                    preferences[PreferenceKeys.selectedYearMoviesId] ?: DEFAULT_PREFERENCE_ID
                GenreAndYearDomain(
                    selectedGenre,
                    selectedGenreId,
                    selectedYear,
                    selectedYeaId
                )
            }
    }

    fun readGenreAndYearShows(): Flow<GenreAndYearDomain> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedGenre =
                    preferences[PreferenceKeys.selectedGenreShows] ?: DEFAULT_GENRE_SHOWS
                val selectedGenreId =
                    preferences[PreferenceKeys.selectedGenreShowsId] ?: DEFAULT_PREFERENCE_ID
                val selectedYear = preferences[PreferenceKeys.selectedYearShows] ?: DEFAULT_YEAR
                val selectedYeaId =
                    preferences[PreferenceKeys.selectedYearShowsId] ?: DEFAULT_PREFERENCE_ID
                GenreAndYearDomain(
                    selectedGenre,
                    selectedGenreId,
                    selectedYear,
                    selectedYeaId
                )
            }
    }

    fun getMoviesLocal(query: HashMap<String, String>): Flow<List<MovieEntity>> {
        val genreIds = query[QUERY_GENRE]?.toInt()
        val genre = mutableListOf<Int>()
        if (genreIds != null) {
            genre.add(genreIds)
        }
        val year = query[QUERY_YEAR_MOVIES].toString()
        return moviesDao.getAllMovies(genre, year)
    }

    fun getShowsLocal(query: HashMap<String, String>): Flow<List<ShowsEntity>> {
        val genreIds = query[QUERY_GENRE]?.toInt()
        val genre = mutableListOf<Int>()
        if (genreIds != null) {
            genre.add(genreIds)
        }
        val year = query[QUERY_YEAR_SHOWS].toString()
        return showsDao.getAllShows(genre, year)
    }

    suspend fun insertMovies(movieList: List<MovieEntity>) = moviesDao.insertMovies(movieList)

    suspend fun insertShows(showsList: List<ShowsEntity>) = showsDao.insertShows(showsList)

    fun getDetailMovies(id: Int): Flow<DetailMoviesEntity> {
        return moviesDao.getDetailMovies(id)
    }

    suspend fun insertDetailMovies(detailMovies: DetailMoviesEntity) =
        moviesDao.insertDetailMovies(detailMovies)

    suspend fun checkDetailMovies(id: Int): Boolean {
        return moviesDao.checkDetailMovies(id)
    }

    fun getDetailShows(id: Int): Flow<DetailShowsEntity> {
        return showsDao.getDetailShows(id)
    }

    suspend fun insertDetailShows(detailShows: DetailShowsEntity) =
        showsDao.insertDetailShows(detailShows)

    suspend fun checkDetailShows(id: Int): Boolean {
        return showsDao.checkDetailShows(id)
    }

    suspend fun insertFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        moviesDao.insertFavoriteMovies(favoriteMovies)
    }

    suspend fun checkFavoriteMovies(id: Int): Boolean {
        return moviesDao.checkFavoriteMovies(id)
    }

    fun getFavoriteMovies(): Flow<List<FavoriteMoviesEntity>> {
        return moviesDao.getFavoriteMovies()
    }

    fun searchFavoriteMovies(query: String): Flow<List<FavoriteMoviesEntity>> {
        return moviesDao.searchFavoriteMovies(query)
    }

    suspend fun deleteFavoriteMoviesById(id: Int) {
        moviesDao.deleteFavoriteMoviesById(id)
    }

    suspend fun deleteFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        moviesDao.deleteFavoriteMovies(favoriteMovies)
    }

    suspend fun deleteAllFavoriteMovies() {
        moviesDao.deleteAllFavoriteMovies()
    }

    suspend fun insertFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        showsDao.insertFavoriteShows(favoriteShows)
    }

    suspend fun checkFavoriteShows(id: Int): Boolean {
        return showsDao.checkFavoriteShows(id)
    }

    fun getFavoriteShows(): Flow<List<FavoriteShowsEntity>> {
        return showsDao.getFavoriteShows()
    }

    fun searchFavoriteShows(query: String): Flow<List<FavoriteShowsEntity>> {
        return showsDao.searchFavoriteShows(query)
    }

    suspend fun deleteFavoriteShowsById(id: Int) {
        showsDao.deleteFavoriteShowsById(id)
    }

    suspend fun deleteFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        showsDao.deleteFavoriteShows(favoriteShows)
    }

    suspend fun deleteAllFavoriteShows() {
        showsDao.deleteAllFavoriteShows()
    }

    fun getUpcomingMovies(): Flow<List<UpcomingMoviesEntity>> {
        return moviesDao.getUpcomingMovies()
    }

    suspend fun insertUpcomingMovies(upcomingMovies: List<UpcomingMoviesEntity>) =
        moviesDao.insertUpcomingMovies(upcomingMovies)

    fun getUpcomingShows(): Flow<List<UpcomingShowsEntity>> {
        return showsDao.getUpcomingShows()
    }

    suspend fun insertUpcomingShows(upcomingShows: List<UpcomingShowsEntity>) =
        showsDao.insertUpcomingShows(upcomingShows)
}