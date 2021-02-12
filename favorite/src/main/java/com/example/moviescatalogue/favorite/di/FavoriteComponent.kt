package com.example.moviescatalogue.favorite.di

import android.content.Context
import com.example.core.di.FavoriteScope
import com.example.moviescatalogue.di.FavoriteModuleDependencies
import com.example.moviescatalogue.favorite.FavoriteActivity
import com.example.moviescatalogue.favorite.FavoriteFragment
import com.example.moviescatalogue.favorite.movies.FavoriteMoviesFragment
import com.example.moviescatalogue.favorite.shows.FavoriteShowsFragment
import dagger.BindsInstance
import dagger.Component

@FavoriteScope
@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: FavoriteMoviesFragment)
    fun inject(fragment: FavoriteShowsFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}