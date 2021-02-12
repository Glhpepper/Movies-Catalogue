package com.example.moviescatalogue.di

import com.example.core.domain.usecase.MainRepositoryUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModuleDependencies {

    fun mainRepositoryUseCase(): MainRepositoryUseCase
}