package com.example.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "shows")
data class ShowsEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "showsId")
    val showsId: String,

    @ColumnInfo(name = "showsTitle")
    val showsTitle: String,

    @ColumnInfo(name = "genreIds")
    val showsGenre: List<Int>,

    @ColumnInfo(name = "showsOverview")
    val showsOverview: String,

    @ColumnInfo(name = "showsPopularity")
    val showsPopularity: Double,

    @ColumnInfo(name = "showsScore")
    val showsScore: String,

    @ColumnInfo(name = "showsImage")
    val showsImage: String?,

    @ColumnInfo(name = "showsDate")
    val showsDate: String
)