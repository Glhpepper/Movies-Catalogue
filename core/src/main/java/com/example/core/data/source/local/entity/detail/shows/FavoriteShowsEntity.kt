package com.example.core.data.source.local.entity.detail.shows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.source.local.entity.detail.GenreEntity

@Entity(tableName = "favorite_shows")
data class FavoriteShowsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "genres")
    val genres: List<GenreEntity>?,

    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "seasons")
    val seasons: List<SeasonsItemEntity>?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "homepage")
    val homepage: String?,
)