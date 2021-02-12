package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.detail.movies.DetailMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * from movies WHERE genreIds LIKE '%'||:genre||'%' AND moviesDate LIKE :year||'%' ORDER BY moviesPopularity DESC")
    fun getAllMovies(genre: List<Int>, year: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovies(detailMovies: DetailMoviesEntity)

    @Query("SELECT * FROM detail_movies WHERE id =:id ")
    fun getDetailMovies(id: Int): Flow<DetailMoviesEntity>

    @Query("SELECT NOT EXISTS(SELECT * FROM detail_movies WHERE id =:id)")
    suspend fun checkDetailMovies(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(favoriteMovies: FavoriteMoviesEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_movies WHERE id =:id)")
    suspend fun checkFavoriteMovies(id: Int): Boolean

    @Query("SELECT * FROM favorite_movies ORDER BY popularity DESC")
    fun getFavoriteMovies(): Flow<List<FavoriteMoviesEntity>>

    @Query("SELECT * FROM favorite_movies WHERE title LIKE '%'||:query||'%' ORDER BY popularity DESC")
    fun searchFavoriteMovies(query: String): Flow<List<FavoriteMoviesEntity>>

    @Query("DELETE FROM favorite_movies WHERE id =:id")
    suspend fun deleteFavoriteMoviesById(id: Int)

    @Delete
    suspend fun deleteFavoriteMovies(favoriteMoviesEntity: FavoriteMoviesEntity)

    @Query("DELETE from favorite_movies")
    suspend fun deleteAllFavoriteMovies()

    @Query("SELECT * from upcoming_movies ORDER BY moviesDate ASC")
    fun getUpcomingMovies(): Flow<List<UpcomingMoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovies(movies: List<UpcomingMoviesEntity>)
}