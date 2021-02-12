package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.ShowsEntity
import com.example.core.data.source.local.entity.detail.shows.DetailShowsEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingShowsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsDao {
    @Query("SELECT * from shows WHERE genreIds LIKE '%'||:genre||'%' AND showsDate LIKE :year||'%' ORDER BY showsPopularity DESC")
    fun getAllShows(genre: List<Int>, year: String): Flow<List<ShowsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShows(shows: List<ShowsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailShows(detailShows: DetailShowsEntity)

    @Query("SELECT * FROM detail_shows WHERE id =:id ")
    fun getDetailShows(id: Int): Flow<DetailShowsEntity>

    @Query("SELECT NOT EXISTS(SELECT * from detail_shows WHERE id =:id)")
    suspend fun checkDetailShows(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteShows(favoriteShows: FavoriteShowsEntity)

    @Query("SELECT EXISTS(SELECT * from favorite_shows WHERE id =:id)")
    suspend fun checkFavoriteShows(id: Int): Boolean

    @Query("SELECT * from favorite_shows ORDER BY popularity DESC")
    fun getFavoriteShows(): Flow<List<FavoriteShowsEntity>>

    @Query("SELECT * FROM favorite_shows WHERE name LIKE '%'||:query||'%' ORDER BY popularity DESC")
    fun searchFavoriteShows(query: String): Flow<List<FavoriteShowsEntity>>

    @Query("DELETE from favorite_shows WHERE id =:id")
    suspend fun deleteFavoriteShowsById(id: Int)

    @Delete
    suspend fun deleteFavoriteShows(favoriteShows: FavoriteShowsEntity)

    @Query("DELETE from favorite_shows")
    suspend fun deleteAllFavoriteShows()

    @Query("SELECT * from upcoming_shows ORDER BY showsDate ASC")
    fun getUpcomingShows(): Flow<List<UpcomingShowsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingShows(shows: List<UpcomingShowsEntity>)
}