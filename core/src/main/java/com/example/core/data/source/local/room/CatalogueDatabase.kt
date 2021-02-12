package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowsEntity
import com.example.core.data.source.local.entity.detail.movies.DetailMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.shows.DetailShowsEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingMoviesEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingShowsEntity

@Database(
    entities = [MovieEntity::class, ShowsEntity::class, DetailMoviesEntity::class, DetailShowsEntity::class,
        FavoriteMoviesEntity::class, FavoriteShowsEntity::class, UpcomingMoviesEntity::class, UpcomingShowsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class CatalogueDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun showsDao(): ShowsDao
}