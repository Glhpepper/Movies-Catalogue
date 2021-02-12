package com.example.core.utils

import com.example.core.BuildConfig

class Constants {
    companion object {
        const val API_KEY = BuildConfig.API_TOKEN
        const val BASE_IMG = BuildConfig.BASE_IMG
        const val BASE_URL = BuildConfig.BASE_URL
        const val PIN_1 = BuildConfig.PIN1
        const val PIN_2 = BuildConfig.PIN2
        const val PIN_3 = BuildConfig.PIN3
        const val HOSTNAME = "api.themoviedb.org"
        const val SHORT_BY = "popularity.desc"
        const val PAGE = "1"
        const val DEFAULT_YEAR = "2020"
        const val DEFAULT_GENRE_MOVIES = "28"
        const val DEFAULT_GENRE_SHOWS = "10759"
        const val ANIMATION_GENRE = "16"
        const val COMEDY_GENRE = "35"
        const val CRIME_GENRE = "80"
        const val HISTORY_GENRE = "36"
        const val HORROR_GENRE = "27"
        const val ROMANCE_GENRE = "10749"
        const val WAR_GENRE = "10752"
        const val MUSIC_GENRE = "10402"
        const val FAMILY_GENRE = "10751"
        const val DOCUMENTARY_GENRE = "99"
        const val DRAMA_GENRE = "18"
        const val KIDS_GENRE = "10762"
        const val POLITICS_GENRE = "10768"
        const val MYSTERY_GENRE = "9648"
        const val UPCOMING_YEAR = "2021"
        const val UPCOMING_SHORT_MOVIES = "release_date.asc"
        const val UPCOMING_SHORT_SHOWS = "first_air_date.asc"

        const val DATA_STORE_NAME = "data_store"
        const val DEFAULT_PREFERENCE_ID = 0
        const val PREFERENCE_GENRE_MOVIES = "genre_movies"
        const val PREFERENCE_GENRE_SHOWS = "genre_shows"
        const val PREFERENCE_GENRE_MOVIES_ID = "genre_movies_id"
        const val PREFERENCE_GENRE_SHOWS_ID = "genre_shows_id"
        const val PREFERENCE_YEAR_MOVIES = "year_movies"
        const val PREFERENCE_YEAR_SHOWS = "year_shows"
        const val PREFERENCE_YEAR_MOVIES_ID = "year_movies_id"
        const val PREFERENCE_YEAR_SHOWS_ID = "year_shows_id"
        const val QUERY_API_KEY = "api_key"
        const val QUERY_SEARCH = "query"
        const val QUERY_SORT_BY = "sort_by"
        const val QUERY_PAGE = "page"
        const val QUERY_YEAR_MOVIES = "year"
        const val QUERY_YEAR_UPCOMING_MOVIES = "primary_release_year"
        const val QUERY_YEAR_UPCOMING_SHOWS = "first_air_date_year"
        const val QUERY_YEAR_SHOWS = "first_air_date_year"
        const val QUERY_GENRE = "with_genres"

        const val OKHTTP_TIMEOUT = 120L
        const val CROSSFADE_DURATION = 600
        const val FIRST_ITEM_VIEWPAGER = 0

        const val DATABASE_NAME = "Catalogue.db"
        const val PASS_PHRASE = "galahseno"
        const val DETAIL_MOVIES_BUNDLE = "moviesBundle"
        const val DETAIL_SHOWS_BUNDLE = "showsBundle"

        const val DEEP_LINK_FAVORITE = "moviescatalogue://favorite"
        const val DEEP_LINK_MOVIES = "moviecatalogue://detail_movies?id="
        const val DEEP_LINK_SHOWS = "moviecatalogue://detail_shows?id="
        const val FAVORITE = "Favorite"
    }
}