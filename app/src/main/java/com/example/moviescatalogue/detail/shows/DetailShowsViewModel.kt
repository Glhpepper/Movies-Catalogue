package com.example.moviescatalogue.detail.shows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.di.CoroutinesModule.IoDispatcher
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.utils.DataMapper
import com.example.moviescatalogue.model.detail.shows.DetailShows
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailShowsViewModel @ViewModelInject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepositoryUseCase: MainRepositoryUseCase
) : ViewModel() {
    fun getDetailShows(id: String): LiveData<Resource<DetailShows>> {
        return mainRepositoryUseCase.getDetailShows(id.toInt()).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapDetailShowsDomainToDetailShows(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun insertFavoriteShows(detailShows: DetailShows) {
        val valueShows = DataMapper.mapDetailShowsToFavoriteShowsEntity(detailShows)
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.insertFavoriteShows(valueShows)
        }
    }

    suspend fun checkFavoriteShows(id: Int): Boolean {
        return withContext(ioDispatcher) {
            mainRepositoryUseCase.checkFavoriteShows(id)
        }
    }

    fun deleteFavoriteShowsById(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            mainRepositoryUseCase.deleteFavoriteShowsById(id)
        }
    }
}