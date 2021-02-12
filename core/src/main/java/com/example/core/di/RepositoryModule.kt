package com.example.core.di

import com.example.core.data.MainRepository
import com.example.core.domain.repository.IMainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class, CoroutinesModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(mainRepository: MainRepository): IMainRepository
}