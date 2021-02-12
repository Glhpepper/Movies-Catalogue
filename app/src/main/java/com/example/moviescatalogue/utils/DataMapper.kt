package com.example.moviescatalogue.utils

import com.example.core.data.source.local.entity.detail.GenreEntity
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.movies.ProductionCompanyEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.local.entity.detail.shows.SeasonsItemEntity
import com.example.core.domain.model.GenreAndYearDomain
import com.example.core.domain.model.detail.GenreDetailDomain
import com.example.core.domain.model.detail.movies.DetailMoviesDomain
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import com.example.core.domain.model.detail.movies.ProductionCompanyDomain
import com.example.core.domain.model.detail.shows.DetailShowsDomain
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import com.example.core.domain.model.detail.shows.SeasonsItemDomain
import com.example.core.domain.model.movies.MoviesDomain
import com.example.core.domain.model.shows.ShowsDomain
import com.example.moviescatalogue.model.GenreAndYear
import com.example.moviescatalogue.model.detail.GenreDetail
import com.example.moviescatalogue.model.detail.movies.DetailMovies
import com.example.moviescatalogue.model.detail.movies.FavoriteMovies
import com.example.moviescatalogue.model.detail.movies.ProductionCompanyDetail
import com.example.moviescatalogue.model.detail.shows.DetailShows
import com.example.moviescatalogue.model.detail.shows.FavoriteShows
import com.example.moviescatalogue.model.detail.shows.SeasonsItemDetail
import com.example.moviescatalogue.model.movies.Movies
import com.example.moviescatalogue.model.shows.Shows

object DataMapper {

    fun mapMoviesDomainToMovies(input: List<MoviesDomain>?): List<Movies> {
        val movieList = ArrayList<Movies>()
        input?.map {
            val movies = Movies(
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

    fun mapMoviesToMoviesDomain(input: List<Movies>?): List<MoviesDomain> {
        val movieList = ArrayList<MoviesDomain>()
        input?.map {
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

    fun mapShowsDomainToShows(input: List<ShowsDomain>?): List<Shows> {
        val showsList = ArrayList<Shows>()
        input?.map {
            val shows = Shows(
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

    fun mapShowsToShowsDomain(input: List<Shows>?): List<ShowsDomain> {
        val showsList = ArrayList<ShowsDomain>()
        input?.map {
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

    fun mapGenreAndYearDomainToPresentation(input: GenreAndYearDomain) =
        GenreAndYear(
            selectedGenre = input.selectedGenre,
            selectedGenreId = input.selectedGenreId,
            selectedYear = input.selectedYear,
            selectedYearId = input.selectedYearId
        )

    fun mapDetailMoviesDomainToDetailMovies(input: DetailMoviesDomain?): DetailMovies {
        val genre = input?.genreDomains?.map {
            GenreDetail(it.id, it.name)
        }
        val productionCompany = input?.productionCompanies?.map {
            ProductionCompanyDetail(it.logoPath, it.name)
        }
        return DetailMovies(
            id = input?.id,
            title = input?.title,
            genres = genre,
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

    fun mapDetailMoviesToDetailMoviesDomain(input: DetailMovies?): DetailMoviesDomain {
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

    fun mapProductionCompanyToDomain(input: List<ProductionCompanyDetail>?): List<ProductionCompanyDomain>? {
        return input?.map {
            ProductionCompanyDomain(it.logoPath, it.name)
        }
    }

    fun mapDetailShowsDomainToDetailShows(input: DetailShowsDomain?): DetailShows {
        val genre = input?.genreDomains?.map {
            GenreDetail(it.id, it.name)
        }
        val season = input?.seasons?.map {
            SeasonsItemDetail(it.name, it.posterPath)
        }
        return DetailShows(
            id = input?.id,
            genres = genre,
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

    fun mapDetailShowsToDetailShowsDomain(input: DetailShows?): DetailShowsDomain {
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

    fun mapSeasonItemToDomain(input: List<SeasonsItemDetail>?): List<SeasonsItemDomain>? {
        return input?.map {
            SeasonsItemDomain(it.name, it.posterPath)
        }
    }

    fun mapDetailMoviesToFavoriteMoviesEntity(input: DetailMovies?): FavoriteMoviesEntity {
        val genre = input?.genres?.map {
            GenreEntity(it.id, it.name)
        }
        val productionCompany = input?.productionCompanies?.map {
            ProductionCompanyEntity(it.logoPath, it.name)
        }
        return FavoriteMoviesEntity(
            id = input?.id,
            title = input?.title,
            genres = genre,
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

    fun mapDetailShowsToFavoriteShowsEntity(input: DetailShows?): FavoriteShowsEntity {
        val genre = input?.genres?.map {
            GenreEntity(it.id, it.name)
        }
        val season = input?.seasons?.map {
            SeasonsItemEntity(it.name, it.posterPath)
        }
        return FavoriteShowsEntity(
            id = input?.id,
            genres = genre,
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

    fun mapFavoriteMoviesDomainToFavoriteMovies(input: List<FavoriteMoviesDomain>?): List<FavoriteMovies> {
        val movieList = ArrayList<FavoriteMovies>()
        input?.map {
            val genre = it.genreDomains?.map { genre ->
                GenreDetail(genre.id, genre.name)
            }
            val productionCompany = it.productionCompanies?.map { production ->
                ProductionCompanyDetail(production.logoPath, production.name)
            }
            val movies = FavoriteMovies(
                id = it.id,
                title = it.title,
                genres = genre,
                popularity = it.popularity,
                voteCount = it.voteCount,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                homepage = it.homepage,
                productionCompanies = productionCompany
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapFavoriteMoviesToFavoriteMoviesDomain(input: List<FavoriteMovies>?): List<FavoriteMoviesDomain> {
        val movieList = ArrayList<FavoriteMoviesDomain>()
        input?.map {
            val genre = it.genres?.map { genre ->
                GenreDetailDomain(genre.id, genre.name)
            }
            val productionCompany = it.productionCompanies?.map { production ->
                ProductionCompanyDomain(production.logoPath, production.name)
            }
            val movies = FavoriteMoviesDomain(
                id = it.id,
                title = it.title,
                genreDomains = genre,
                popularity = it.popularity,
                voteCount = it.voteCount,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                homepage = it.homepage,
                productionCompanies = productionCompany
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapFavoriteShowsDomainToFavoriteShows(input: List<FavoriteShowsDomain>?): List<FavoriteShows> {
        val showsList = ArrayList<FavoriteShows>()
        input?.map {
            val genre = it.genreDomains?.map { genre ->
                GenreDetail(genre.id, genre.name)
            }
            val season = it.seasons?.map { production ->
                SeasonsItemDetail(production.name, production.posterPath)
            }
            val shows = FavoriteShows(
                id = it.id,
                genres = genre,
                popularity = it.popularity,
                voteCount = it.voteCount,
                firstAirDate = it.firstAirDate,
                overview = it.overview,
                seasons = season,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                name = it.name,
                homepage = it.homepage,
            )
            showsList.add(shows)
        }
        return showsList
    }

    fun mapFavoriteShowsToFavoriteShowsDomain(input: List<FavoriteShows>?): List<FavoriteShowsDomain> {
        val showsList = ArrayList<FavoriteShowsDomain>()
        input?.map {
            val genre = it.genres?.map { genre ->
                GenreDetailDomain(genre.id, genre.name)
            }
            val season = it.seasons?.map { production ->
                SeasonsItemDomain(production.name, production.posterPath)
            }
            val shows = FavoriteShowsDomain(
                id = it.id,
                genreDomains = genre,
                popularity = it.popularity,
                voteCount = it.voteCount,
                firstAirDate = it.firstAirDate,
                overview = it.overview,
                seasons = season,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                name = it.name,
                homepage = it.homepage,
            )
            showsList.add(shows)
        }
        return showsList
    }

    fun mapFavoriteMoviesToFavoriteMoviesEntity(input: FavoriteMoviesDomain?): FavoriteMoviesEntity {
        val genre = input?.genreDomains?.map {
            GenreEntity(it.id, it.name)
        }
        val productionCompany = input?.productionCompanies?.map {
            ProductionCompanyEntity(it.logoPath, it.name)
        }
        return FavoriteMoviesEntity(
            id = input?.id,
            title = input?.title,
            genres = genre,
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

    fun mapFavoriteShowsToFavoriteShowsEntity(input: FavoriteShowsDomain?): FavoriteShowsEntity {
        val genre = input?.genreDomains?.map {
            GenreEntity(it.id, it.name)
        }
        val season = input?.seasons?.map {
            SeasonsItemEntity(it.name, it.posterPath)
        }
        return FavoriteShowsEntity(
            id = input?.id,
            genres = genre,
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
}