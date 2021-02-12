package com.example.moviescatalogue.detail.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.di.CoroutinesModule.IoDispatcher
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.utils.DataMapper
import com.example.moviescatalogue.model.detail.movies.DetailMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMoviesViewModel @ViewModelInject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepositoryUseCase: MainRepositoryUseCase
) : ViewModel() {

    fun getDetailMovies(id: String): LiveData<Resource<DetailMovies>> {
        return mainRepositoryUseCase.getDetailMovies(id.toInt()).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapDetailMoviesDomainToDetailMovies(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun insertFavoriteMovies(detailMovies: DetailMovies) {
        val valueMovies = DataMapper.mapDetailMoviesToFavoriteMoviesEntity(detailMovies)
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.insertFavoriteMovies(valueMovies)
        }
    }

    suspend fun checkFavoriteMovies(id: Int): Boolean {
        return withContext(ioDispatcher) {
            mainRepositoryUseCase.checkFavoriteMovies(id)
        }
    }

    fun deleteFavoriteMoviesById(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.deleteFavoriteMoviesById(id)
        }
    }
}
