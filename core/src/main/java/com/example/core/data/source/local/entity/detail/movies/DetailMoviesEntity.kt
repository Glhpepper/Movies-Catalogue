package com.example.core.data.source.local.entity.detail.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.source.local.entity.detail.GenreEntity
import org.jetbrains.annotations.NotNull

@Entity(tableName = "detail_movies")
data class DetailMoviesEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "genres")
    val genres: List<GenreEntity>,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "homepage")
    val homepage: String,

    @ColumnInfo(name = "production_companies")
    val productionCompanies: List<ProductionCompanyEntity>
)