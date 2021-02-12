package com.example.core.data.source.local.room

import androidx.room.TypeConverter
import com.example.core.data.source.local.entity.detail.GenreEntity
import com.example.core.data.source.local.entity.detail.movies.ProductionCompanyEntity
import com.example.core.data.source.local.entity.detail.shows.SeasonsItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromStringToListInt(value: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromListIntToString(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun fromStringToListGenre(value: String): List<GenreEntity> {
        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromListGenreToString(list: List<GenreEntity>): String = Gson().toJson(list)

    @TypeConverter
    fun fromStringToListProductionCompany(value: String): List<ProductionCompanyEntity> {
        val type = object : TypeToken<List<ProductionCompanyEntity>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromListProductionCompanyToString(list: List<ProductionCompanyEntity>): String =
        Gson().toJson(list)

    @TypeConverter
    fun fromStringToListSeason(value: String): List<SeasonsItemEntity> {
        val type = object : TypeToken<List<SeasonsItemEntity>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromListSeasonToString(list: List<SeasonsItemEntity>): String = Gson().toJson(list)
}