package com.example.moviescatalogue.upcoming

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.core.data.Resource
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.model.movies.Movies
import com.example.moviescatalogue.model.shows.Shows
import com.example.moviescatalogue.utils.DataMapper

class UpcomingViewModel @ViewModelInject constructor(
    private val mainRepositoryUseCase: MainRepositoryUseCase
) : ViewModel() {

    fun getUpcomingMovies(query: HashMap<String, String>): LiveData<Resource<List<Movies>>> {
        return mainRepositoryUseCase.getUpcomingMovies(query).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapMoviesDomainToMovies(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun getUpcomingShows(query: HashMap<String, String>): LiveData<Resource<List<Shows>>> {
        return mainRepositoryUseCase.getUpcomingShows(query).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapShowsDomainToShows(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }
}