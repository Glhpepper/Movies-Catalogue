package com.example.moviescatalogue.di

import com.example.core.domain.usecase.MainRepositoryInteractor
import com.example.core.domain.usecase.MainRepositoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMainRepositoryUseCase(mainRepositoryInteractor: MainRepositoryInteractor): MainRepositoryUseCase
}