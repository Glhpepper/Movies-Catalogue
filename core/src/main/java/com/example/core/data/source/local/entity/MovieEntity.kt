package com.example.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "moviesId")
    val moviesId: String,

    @ColumnInfo(name = "moviesTitle")
    val moviesTitle: String,

    @ColumnInfo(name = "genreIds")
    val moviesGenre: List<Int>,

    @ColumnInfo(name = "moviesOverview")
    val moviesOverview: String,

    @ColumnInfo(name = "moviesPopularity")
    val moviesPopularity: Double,

    @ColumnInfo(name = "moviesScore")
    val moviesScore: String,

    @ColumnInfo(name = "moviesImage")
    val moviesImage: String?,

    @ColumnInfo(name = "moviesDate")
    val moviesDate: String
)