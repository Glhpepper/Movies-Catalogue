package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.source.local.room.CatalogueDatabase
import com.example.core.data.source.local.room.MoviesDao
import com.example.core.data.source.local.room.ShowsDao
import com.example.core.utils.Constants.Companion.DATABASE_NAME
import com.example.core.utils.Constants.Companion.PASS_PHRASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CatalogueDatabase {
        val passphrase = SQLiteDatabase.getBytes(PASS_PHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            CatalogueDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(database: CatalogueDatabase): MoviesDao = database.moviesDao()

    @Singleton
    @Provides
    fun provideShowsDao(database: CatalogueDatabase): ShowsDao = database.showsDao()
}