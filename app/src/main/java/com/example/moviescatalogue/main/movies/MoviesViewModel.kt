package com.example.moviescatalogue.main.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.di.CoroutinesModule.IoDispatcher
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.utils.DataMapper
import com.example.moviescatalogue.model.GenreAndYear
import com.example.moviescatalogue.model.movies.Movies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepositoryUseCase: MainRepositoryUseCase
) :
    ViewModel() {

    fun getMovies(query: HashMap<String, String>): LiveData<Resource<List<Movies>>> {
        return mainRepositoryUseCase.getMovies(query).asLiveData().map { resource ->
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

    fun searchMovies(query: HashMap<String, String>): LiveData<Resource<List<Movies>>> {
        return mainRepositoryUseCase.searchMovies(query).asLiveData().map { resource ->
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

    fun saveGenreAndYear(genre: String, genreId: Int, year: String, yearId: Int) {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.saveGenreAndYearMovies(genre, genreId, year, yearId)
        }
    }

    fun readGenreAndYear(): LiveData<GenreAndYear> {
        return mainRepositoryUseCase.readGenreAndYearMovies().asLiveData().map { genreAndYear ->
            DataMapper.mapGenreAndYearDomainToPresentation(genreAndYear)
        }
    }
}