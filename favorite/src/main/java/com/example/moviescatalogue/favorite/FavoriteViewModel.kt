package com.example.moviescatalogue.favorite

import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.di.FavoriteScope
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.model.detail.movies.FavoriteMovies
import com.example.moviescatalogue.model.detail.shows.FavoriteShows
import com.example.moviescatalogue.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@FavoriteScope
class FavoriteViewModel(
    private val mainRepositoryUseCase: MainRepositoryUseCase
) : ViewModel() {

    private val ioDispatcher = Dispatchers.IO

    fun getFavoriteMovies(): LiveData<Resource<List<FavoriteMovies>>> {
        return mainRepositoryUseCase.getFavoriteMovies().asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapFavoriteMoviesDomainToFavoriteMovies(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun searchFavoriteMovies(query: String): LiveData<List<FavoriteMovies>> {
        return mainRepositoryUseCase.searchFavoriteMovies(query).asLiveData().map { movies ->
            DataMapper.mapFavoriteMoviesDomainToFavoriteMovies(movies)
        }
    }

    fun getFavoriteShows(): LiveData<Resource<List<FavoriteShows>>> {
        return mainRepositoryUseCase.getFavoriteShows().asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapFavoriteShowsDomainToFavoriteShows(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun searchFavoriteShows(query: String): LiveData<List<FavoriteShows>> {
        return mainRepositoryUseCase.searchFavoriteShows(query).asLiveData().map { movies ->
            DataMapper.mapFavoriteShowsDomainToFavoriteShows(movies)
        }
    }

    fun insertFavoriteMovies(favoriteMoviesDomain: FavoriteMoviesDomain) {
        viewModelScope.launch(ioDispatcher) {
            val moviesEntity =
                DataMapper.mapFavoriteMoviesToFavoriteMoviesEntity(favoriteMoviesDomain)
            mainRepositoryUseCase.insertFavoriteMovies(moviesEntity)
        }
    }

    fun deleteFavoriteMovies(favoriteMoviesDomain: FavoriteMoviesDomain) {
        viewModelScope.launch(ioDispatcher) {
            val moviesEntity =
                DataMapper.mapFavoriteMoviesToFavoriteMoviesEntity(favoriteMoviesDomain)
            mainRepositoryUseCase.deleteFavoriteMovies(moviesEntity)
        }
    }

    fun deleteAllFavoriteMovies() {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.deleteAllFavoriteMovies()
        }
    }

    fun insertFavoriteShows(favoriteShowsDomain: FavoriteShowsDomain) {
        viewModelScope.launch(ioDispatcher) {
            val showsEntity = DataMapper.mapFavoriteShowsToFavoriteShowsEntity(favoriteShowsDomain)
            mainRepositoryUseCase.insertFavoriteShows(showsEntity)
        }
    }

    fun deleteFavoriteShows(favoriteShowsDomain: FavoriteShowsDomain) {
        viewModelScope.launch(ioDispatcher) {
            val showsEntity = DataMapper.mapFavoriteShowsToFavoriteShowsEntity(favoriteShowsDomain)
            mainRepositoryUseCase.deleteFavoriteShows(showsEntity)
        }
    }

    fun deleteAllFavoriteShows() {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.deleteAllFavoriteShows()
        }
    }
}