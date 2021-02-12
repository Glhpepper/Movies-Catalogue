package com.example.moviescatalogue.main.shows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.di.CoroutinesModule.IoDispatcher
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.utils.DataMapper
import com.example.moviescatalogue.model.GenreAndYear
import com.example.moviescatalogue.model.shows.Shows
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ShowsViewModel @ViewModelInject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepositoryUseCase: MainRepositoryUseCase
) : ViewModel() {

    fun getShows(query: HashMap<String, String>): LiveData<Resource<List<Shows>>> {
        return mainRepositoryUseCase.getShows(query).asLiveData().map { resource ->
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

    fun searchShows(query: HashMap<String, String>): LiveData<Resource<List<Shows>>> {
        return mainRepositoryUseCase.searchShows(query).asLiveData().map { resource ->
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

    fun saveGenreAndYear(genre: String, genreId: Int, year: String, yearId: Int) {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.saveGenreAndYearShows(genre, genreId, year, yearId)
        }
    }

    fun readGenreAndYear(): LiveData<GenreAndYear> {
        return mainRepositoryUseCase.readGenreAndYearShows().asLiveData().map { genreAndYear ->
            DataMapper.mapGenreAndYearDomainToPresentation(genreAndYear)
        }
    }
}