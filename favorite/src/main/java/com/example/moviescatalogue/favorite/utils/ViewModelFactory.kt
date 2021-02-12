package com.example.moviescatalogue.favorite.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.domain.usecase.MainRepositoryUseCase
import com.example.moviescatalogue.favorite.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val mainRepositoryUseCase: MainRepositoryUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mainRepositoryUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}