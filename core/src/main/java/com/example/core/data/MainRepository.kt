package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.detail.movies.FavoriteMoviesEntity
import com.example.core.data.source.local.entity.detail.shows.FavoriteShowsEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.movies.ResponseMovies
import com.example.core.data.source.remote.response.movies.detail.ResponseDetailMovies
import com.example.core.data.source.remote.response.shows.ResponseShows
import com.example.core.data.source.remote.response.shows.detail.ResponseDetailShows
import com.example.core.di.CoroutinesModule.IoDispatcher
import com.example.core.domain.model.GenreAndYearDomain
import com.example.core.domain.model.detail.movies.DetailMoviesDomain
import com.example.core.domain.model.detail.movies.FavoriteMoviesDomain
import com.example.core.domain.model.detail.shows.DetailShowsDomain
import com.example.core.domain.model.detail.shows.FavoriteShowsDomain
import com.example.core.domain.model.movies.MoviesDomain
import com.example.core.domain.model.shows.ShowsDomain
import com.example.core.domain.repository.IMainRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IMainRepository {

    override fun getMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return object : NetworkBoundResource<List<MoviesDomain>, List<ResponseMovies>>() {
            override fun loadFromDB(): Flow<List<MoviesDomain>> {
                return localDataSource.getMoviesLocal(query).map {
                    DataMapper.mapMoviesEntityToDomainMovies(it)
                }
            }

            override suspend fun shouldFetch(data: List<MoviesDomain>): Boolean {
                return data.size < 10
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovies>>> {
                return remoteDataSource.getMoviesApi(query)
            }

            override suspend fun saveCallResult(data: List<ResponseMovies>) {
                val movieList = DataMapper.mapResponsesToEntitiesMovies(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return object : NetworkBoundResource<List<ShowsDomain>, List<ResponseShows>>() {
            override fun loadFromDB(): Flow<List<ShowsDomain>> {
                return localDataSource.getShowsLocal(query).map {
                    DataMapper.mapShowsEntityToDomainShows(it)
                }
            }

            override suspend fun shouldFetch(data: List<ShowsDomain>): Boolean {
                return data.size < 10
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseShows>>> {
                return remoteDataSource.getShowsApi(query)
            }

            override suspend fun saveCallResult(data: List<ResponseShows>) {
                val showsList = DataMapper.mapResponsesToEntitiesShows(data)
                localDataSource.insertShows(showsList)
            }
        }.asFlow()
    }

    override suspend fun saveGenreAndYearMovies(
        genre: String, genreId: Int, year: String, yearId: Int
    ) {
        localDataSource.saveGenreAndYearMovies(genre, genreId, year, yearId)
    }

    override suspend fun saveGenreAndYearShows(
        genre: String, genreId: Int, year: String, yearId: Int
    ) {
        localDataSource.saveGenreAndYearShows(genre, genreId, year, yearId)
    }

    override fun readGenreAndYearMovies(): Flow<GenreAndYearDomain> {
        return localDataSource.readGenreAndYearMovies()
    }

    override fun readGenreAndYearShows(): Flow<GenreAndYearDomain> {
        return localDataSource.readGenreAndYearShows()
    }

    override fun getDetailMovies(id: Int): Flow<Resource<DetailMoviesDomain>> {
        return object : NetworkBoundResource<DetailMoviesDomain, ResponseDetailMovies>() {
            override fun loadFromDB(): Flow<DetailMoviesDomain> {
                return localDataSource.getDetailMovies(id).map {
                    DataMapper.mapEntitiesToDomainDetailMovies(it)
                }
            }

            override suspend fun shouldFetch(data: DetailMoviesDomain): Boolean {
                return localDataSource.checkDetailMovies(id)
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailMovies>> {
                return remoteDataSource.getDetailMovies(id.toString())
            }

            override suspend fun saveCallResult(data: ResponseDetailMovies) {
                val detailMovies = DataMapper.mapResponsesToEntitiesDetailMoviesEntity(data)
                localDataSource.insertDetailMovies(detailMovies)
            }
        }.asFlow()
    }

    override fun getDetailShows(id: Int): Flow<Resource<DetailShowsDomain>> {
        return object : NetworkBoundResource<DetailShowsDomain, ResponseDetailShows>() {
            override fun loadFromDB(): Flow<DetailShowsDomain> {
                return localDataSource.getDetailShows(id).map {
                    DataMapper.mapEntitiesToDomainDetailShows(it)
                }
            }

            override suspend fun shouldFetch(data: DetailShowsDomain): Boolean {
                return localDataSource.checkDetailShows(id)
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailShows>> {
                return remoteDataSource.getDetailShows(id.toString())
            }

            override suspend fun saveCallResult(data: ResponseDetailShows) {
                val detailShows = DataMapper.mapResponsesToEntitiesDetailShowsEntity(data)
                localDataSource.insertDetailShows(detailShows)
            }
        }.asFlow()
    }

    override suspend fun insertFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        localDataSource.insertFavoriteMovies(favoriteMovies)
    }

    override suspend fun checkFavoriteMovies(id: Int): Boolean {
        return localDataSource.checkFavoriteMovies(id)
    }

    override fun getFavoriteMovies(): Flow<Resource<List<FavoriteMoviesDomain>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val data = localDataSource.getFavoriteMovies()
                if (data.first().isNotEmpty()) {
                    val favoriteMovies = data.map {
                        DataMapper.mapEntitiesToDomainFavoriteMovies(it)
                    }
                    emitAll(favoriteMovies.map { Resource.Success(it) })
                } else {
                    emit(Resource.Success(emptyList<FavoriteMoviesDomain>()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<List<FavoriteMoviesDomain>>(e.message.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    override fun searchFavoriteMovies(query: String): Flow<List<FavoriteMoviesDomain>> {
        return localDataSource.searchFavoriteMovies(query).map {
            DataMapper.mapEntitiesToDomainFavoriteMovies(it)
        }
    }

    override suspend fun deleteFavoriteMoviesById(id: Int) {
        localDataSource.deleteFavoriteMoviesById(id)
    }

    override suspend fun deleteFavoriteMovies(favoriteMovies: FavoriteMoviesEntity) {
        localDataSource.deleteFavoriteMovies(favoriteMovies)
    }

    override suspend fun deleteAllFavoriteMovies() {
        localDataSource.deleteAllFavoriteMovies()
    }

    override suspend fun insertFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        localDataSource.insertFavoriteShows(favoriteShows)
    }

    override suspend fun checkFavoriteShows(id: Int): Boolean {
        return localDataSource.checkFavoriteShows(id)
    }

    override fun getFavoriteShows(): Flow<Resource<List<FavoriteShowsDomain>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val data = localDataSource.getFavoriteShows()
                if (data.first().isNotEmpty()) {
                    val favoriteShows = data.map {
                        DataMapper.mapEntitiesToDomainFavoriteShows(it)
                    }
                    emitAll(favoriteShows.map { Resource.Success(it) })
                } else {
                    emit(Resource.Success(emptyList<FavoriteShowsDomain>()))
                }
            } catch (e: Exception) {
                emit(Resource.Error<List<FavoriteShowsDomain>>(e.message.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    override fun searchFavoriteShows(query: String): Flow<List<FavoriteShowsDomain>> {
        return localDataSource.searchFavoriteShows(query).map {
            DataMapper.mapEntitiesToDomainFavoriteShows(it)
        }
    }

    override suspend fun deleteFavoriteShowsById(id: Int) {
        localDataSource.deleteFavoriteShowsById(id)
    }

    override suspend fun deleteFavoriteShows(favoriteShows: FavoriteShowsEntity) {
        localDataSource.deleteFavoriteShows(favoriteShows)
    }

    override suspend fun deleteAllFavoriteShows() {
        localDataSource.deleteAllFavoriteShows()
    }

    override fun getUpcomingMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return object : NetworkBoundResource<List<MoviesDomain>, List<ResponseMovies>>() {
            override fun loadFromDB(): Flow<List<MoviesDomain>> {
                return localDataSource.getUpcomingMovies().map {
                    DataMapper.mapUpcomingMoviesEntityToDomainMovies(it)
                }
            }

            override suspend fun shouldFetch(data: List<MoviesDomain>): Boolean {
                return data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovies>>> {
                return remoteDataSource.getMoviesApi(query)
            }

            override suspend fun saveCallResult(data: List<ResponseMovies>) {
                val upcomingMovies = DataMapper.mapResponsesToUpcomingMovies(data)
                localDataSource.insertUpcomingMovies(upcomingMovies)
            }
        }.asFlow()
    }

    override fun getUpcomingShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return object : NetworkBoundResource<List<ShowsDomain>, List<ResponseShows>>() {
            override fun loadFromDB(): Flow<List<ShowsDomain>> {
                return localDataSource.getUpcomingShows().map {
                    DataMapper.mapUpcomingShowsEntityToDomainShows(it)
                }
            }

            override suspend fun shouldFetch(data: List<ShowsDomain>): Boolean {
                return data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseShows>>> {
                return remoteDataSource.getShowsApi(query)
            }

            override suspend fun saveCallResult(data: List<ResponseShows>) {
                val upcomingShows = DataMapper.mapResponsesToUpcomingShows(data)
                localDataSource.insertUpcomingShows(upcomingShows)
            }
        }.asFlow()
    }

    override fun searchMovies(query: HashMap<String, String>): Flow<Resource<List<MoviesDomain>>> {
        return flow {
            emit(Resource.Loading())
            val data = remoteDataSource.searchMoviesApi(query)
            when (val response = data.first()) {
                is ApiResponse.Success -> {
                    val movieList = data.map {
                        DataMapper.mapResponsesToDomainMovies(response.data)
                    }
                    emitAll(movieList.map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList<MoviesDomain>()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<MoviesDomain>>(response.errorMessage))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override fun searchShows(query: HashMap<String, String>): Flow<Resource<List<ShowsDomain>>> {
        return flow {
            emit(Resource.Loading())
            val data = remoteDataSource.searchShowsApi(query)
            when (val response = data.first()) {
                is ApiResponse.Success -> {
                    val showsList = data.map {
                        DataMapper.mapResponsesToDomainShows(response.data)
                    }
                    emitAll(showsList.map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList<ShowsDomain>()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<ShowsDomain>>(response.errorMessage))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
