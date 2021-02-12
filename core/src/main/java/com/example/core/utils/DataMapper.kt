package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowsEntity
import com.example.core.data.source.local.entity.detail.GenreEntity
import com.example.core.data.source.local.entity.detail.movies.DetailMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.ProductionCompanyEntity
import com.example.core.data.source.local.entity.detail.shows.DetailShowsEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.local.entity.detail.shows.SeasonsItemEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingMoviesEntity
import com.example.core.data.source.local.entity.upcoming.UpcomingShowsEntity
import com.example.core.data.source.remote.response.movies.ResponseMovies
import com.example.core.data.source.remote.response.movies.detail.ResponseDetailMovies
import com.example.core.data.source.remote.response.shows.ResponseShows
import com.example.core.data.source.remote.response.shows.detail.ResponseDetailShows
import com.example.core.domain.model.detail.GenreDetailDomain
import com.example.core.domain.model.detail.movies.DetailMoviesDomain
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import com.example.core.domain.model.detail.movies.ProductionCompanyDomain
import com.example.core.domain.model.detail.shows.DetailShowsDomain
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import com.example.core.domain.model.detail.shows.SeasonsItemDomain
import com.example.core.domain.model.movies.MoviesDomain
import com.example.core.domain.model.shows.ShowsDomain

object DataMapper {
    fun mapResponsesToEntitiesMovies(input: List<ResponseMovies>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movies = MovieEntity(
                moviesId = it.moviesId,
                moviesTitle = it.moviesTitle,
                moviesGenre = it.moviesGenre,
                moviesOverview = it.moviesOverview,
                moviesPopularity = it.moviesPopularity,
                moviesScore = it.moviesScore,
                moviesImage = it.moviesImage,
                moviesDate = it.moviesDate
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapResponsesToEntitiesShows(input: List<ResponseShows>): List<ShowsEntity> {
        val showsList = ArrayList<ShowsEntity>()
        input.map {
            val shows = ShowsEntity(
                showsId = it.showsId,
                showsTitle = it.showsTitle,
                showsGenre = it.showsGenre,
                showsOverview = it.showsOverview,
                showsPopularity = it.showsPopularity,
                showsScore = it.showsScore,
                showsImage = it.showsImage,
                showsDate = it.showsDate
            )
            showsList.add(shows)
        }
        return showsList
    }

    fun mapResponsesToEntitiesDetailMoviesEntity(input: ResponseDetailMovies): DetailMoviesEntity {
        val genre = input.genres.map {
            GenreEntity(it.id, it.name)
        }
        val productionCompany = input.productionCompanies.map {
            ProductionCompanyEntity(it.logoPath, it.name)
        }
        return DetailMoviesEntity(
            id = input.id,
            title = input.title,
            genres = genre,
            popularity = input.popularity,
            voteCount = input.voteCount,
            overview = input.overview,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath.toString(),
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            homepage = input.homepage,
            productionCompanies = productionCompany
        )
    }

    fun mapResponsesToEntitiesDetailShowsEntity(input: ResponseDetailShows): DetailShowsEntity {
        val genre = input.genres.map {
            GenreEntity(it.id, it.name)
        }
        val season = input.seasons.map {
            SeasonsItemEntity(it.name, it.posterPath)
        }
        return DetailShowsEntity(
            id = input.id,
            genres = genre,
            popularity = input.popularity,
            voteCount = input.voteCount,
            firstAirDate = input.firstAirDate,
            overview = input.overview,
            seasons = season,
            posterPath = input.posterPath.toString(),
            voteAverage = input.voteAverage,
            name = input.name,
            homepage = input.homepage,
        )
    }

    fun mapMoviesEntityToDomainMovies(input: List<MovieEntity>): List<MoviesDomain> =
        input.map {
            MoviesDomain(
                moviesId = it.moviesId,
                moviesTitle = it.moviesTitle,
                moviesGenre = it.moviesGenre,
                moviesOverview = it.moviesOverview,
                moviesPopularity = it.moviesPopularity,
                moviesScore = it.moviesScore,
                moviesImage = it.moviesImage,
                moviesDate = it.moviesDate
            )
        }

    fun mapShowsEntityToDomainShows(input: List<ShowsEntity>): List<ShowsDomain> =
        input.map {
            ShowsDomain(
                showsId = it.showsId,
                showsTitle = it.showsTitle,
                showsGenre = it.showsGenre,
                showsOverview = it.showsOverview,
                showsPopularity = it.showsPopularity,
                showsScore = it.showsScore,
                showsImage = it.showsImage,
                showsDate = it.showsDate
            )
        }

    fun mapEntitiesToDomainDetailMovies(input: DetailMoviesEntity?): DetailMoviesDomain {
        val genre = input?.genres?.map {
            GenreDetailDomain(it.id, it.name)
        }
        val productionCompany = input?.productionCompanies?.map {
            ProductionCompanyDomain(it.logoPath, it.name)
        }
        return DetailMoviesDomain(
            id = input?.id,
            title = input?.title,
            genreDomains = genre,
            popularity = input?.popularity,
            voteCount = input?.voteCount,
            overview = input?.overview,
            posterPath = input?.posterPath,
            backdropPath = input?.backdropPath,
            releaseDate = input?.releaseDate,
            voteAverage = input?.voteAverage,
            homepage = input?.homepage,
            productionCompanies = productionCompany
        )
    }

    fun mapEntitiesToDomainDetailShows(input: DetailShowsEntity?): DetailShowsDomain {
        val genre = input?.genres?.map {
            GenreDetailDomain(it.id, it.name)
        }
        val season = input?.seasons?.map {
            SeasonsItemDomain(it.name, it.posterPath)
        }
        return DetailShowsDomain(
            id = input?.id,
            genreDomains = genre,
            popularity = input?.popularity,
            voteCount = input?.voteCount,
            firstAirDate = input?.firstAirDate,
            overview = input?.overview,
            seasons = season,
            posterPath = input?.posterPath,
            voteAverage = input?.voteAverage,
            name = input?.name,
            homepage = input?.homepage,
        )
    }

    fun mapEntitiesToDomainFavoriteMovies(input: List<FavoriteMoviesEntity>?): List<FavoriteMoviesDomain> {
        val favoriteMovies = ArrayList<FavoriteMoviesDomain>()
        input?.map { movies ->
            val genre = movies.genres?.map {
                GenreDetailDomain(it.id, it.name)
            }
            val productionCompany = movies.productionCompanies?.map {
                ProductionCompanyDomain(it.logoPath, it.name)
            }
            val favorite = FavoriteMoviesDomain(
                id = movies.id,
                title = movies.title,
                genreDomains = genre,
                popularity = movies.popularity,
                voteCount = movies.voteCount,
                overview = movies.overview,
                posterPath = movies.posterPath,
                backdropPath = movies.backdropPath,
                releaseDate = movies.releaseDate,
                voteAverage = movies.voteAverage,
                homepage = movies.homepage,
                productionCompanies = productionCompany
            )
            favoriteMovies.add(favorite)
        }
        return favoriteMovies
    }

    fun mapEntitiesToDomainFavoriteShows(input: List<FavoriteShowsEntity>?): List<FavoriteShowsDomain> {
        val favoriteShows = ArrayList<FavoriteShowsDomain>()
        input?.map { shows ->
            val genre = shows.genres?.map {
                GenreDetailDomain(it.id, it.name)
            }
            val season = shows.seasons?.map {
                SeasonsItemDomain(it.name, it.posterPath)
            }
            val favorite = FavoriteShowsDomain(
                id = shows.id,
                genreDomains = genre,
                popularity = shows.popularity,
                voteCount = shows.voteCount,
                firstAirDate = shows.firstAirDate,
                overview = shows.overview,
                seasons = season,
                posterPath = shows.posterPath,
                voteAverage = shows.voteAverage,
                name = shows.name,
                homepage = shows.homepage,
            )
            favoriteShows.add(favorite)
        }
        return favoriteShows
    }

    fun mapResponsesToUpcomingMovies(input: List<ResponseMovies>): List<UpcomingMoviesEntity> {
        val movieList = ArrayList<UpcomingMoviesEntity>()
        input.map {
            val movies = UpcomingMoviesEntity(
                moviesId = it.moviesId,
                moviesTitle = it.moviesTitle,
                moviesGenre = it.moviesGenre,
                moviesOverview = it.moviesOverview,
                moviesPopularity = it.moviesPopularity,
                moviesScore = it.moviesScore,
                moviesImage = it.moviesImage,
                moviesDate = it.moviesDate
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapResponsesToUpcomingShows(input: List<ResponseShows>): List<UpcomingShowsEntity> {
        val movieList = ArrayList<UpcomingShowsEntity>()
        input.map {
            val movies = UpcomingShowsEntity(
                showsId = it.showsId,
                showsTitle = it.showsTitle,
                showsGenre = it.showsGenre,
                showsOverview = it.showsOverview,
                showsPopularity = it.showsPopularity,
                showsScore = it.showsScore,
                showsImage = it.showsImage,
                showsDate = it.showsDate
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapUpcomingMoviesEntityToDomainMovies(input: List<UpcomingMoviesEntity>): List<MoviesDomain> =
        input.map {
            MoviesDomain(
                moviesId = it.moviesId,
                moviesTitle = it.moviesTitle,
                moviesGenre = it.moviesGenre,
                moviesOverview = it.moviesOverview,
                moviesPopularity = it.moviesPopularity,
                moviesScore = it.moviesScore,
                moviesImage = it.moviesImage,
                moviesDate = it.moviesDate
            )
        }

    fun mapUpcomingShowsEntityToDomainShows(input: List<UpcomingShowsEntity>): List<ShowsDomain> =
        input.map {
            ShowsDomain(
                showsId = it.showsId,
                showsTitle = it.showsTitle,
                showsGenre = it.showsGenre,
                showsOverview = it.showsOverview,
                showsPopularity = it.showsPopularity,
                showsScore = it.showsScore,
                showsImage = it.showsImage,
                showsDate = it.showsDate
            )
        }

    fun mapResponsesToDomainMovies(input: List<ResponseMovies>): List<MoviesDomain> {
        val movieList = ArrayList<MoviesDomain>()
        input.map {
            val movies = MoviesDomain(
                moviesId = it.moviesId,
                moviesTitle = it.moviesTitle,
                moviesGenre = it.moviesGenre,
                moviesOverview = it.moviesOverview,
                moviesPopularity = it.moviesPopularity,
                moviesScore = it.moviesScore,
                moviesImage = it.moviesImage,
                moviesDate = it.moviesDate
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapResponsesToDomainShows(input: List<ResponseShows>): List<ShowsDomain> {
        val showsList = ArrayList<ShowsDomain>()
        input.map {
            val shows = ShowsDomain(
                showsId = it.showsId,
                showsTitle = it.showsTitle,
                showsGenre = it.showsGenre,
                showsOverview = it.showsOverview,
                showsPopularity = it.showsPopularity,
                showsScore = it.showsScore,
                showsImage = it.showsImage,
                showsDate = it.showsDate
            )
            showsList.add(shows)
        }
        return showsList
    }
}