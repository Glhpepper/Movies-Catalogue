package com.example.core.data.source.remote

import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.movies.ResponseMovies
import com.example.core.data.source.remote.response.movies.detail.ResponseDetailMovies
import com.example.core.data.source.remote.response.shows.ResponseShows
import com.example.core.data.source.remote.response.shows.detail.ResponseDetailShows
import com.example.core.di.CoroutinesModule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getMoviesApi(query: HashMap<String, String>): Flow<ApiResponse<List<ResponseMovies>>> {
        return flow {
            try {
                val response = apiService.getMovie(query)
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getShowsApi(query: HashMap<String, String>): Flow<ApiResponse<List<ResponseShows>>> {
        return flow {
            try {
                val response = apiService.getShows(query)
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getDetailMovies(id: String): Flow<ApiResponse<ResponseDetailMovies>> {
        return flow {
            try {
                val response = apiService.getDetailMovies(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getDetailShows(id: String): Flow<ApiResponse<ResponseDetailShows>> {
        return flow {
            try {
                val response = apiService.getDetailTvShows(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun searchMoviesApi(query: HashMap<String, String>): Flow<ApiResponse<List<ResponseMovies>>> {
        return flow {
            val response = apiService.searchMovie(query)
            val data = response.results
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        }.flowOn(ioDispatcher)
            .catch { emit(ApiResponse.Error(it.message.toString())) }
    }

    suspend fun searchShowsApi(query: HashMap<String, String>): Flow<ApiResponse<List<ResponseShows>>> {
        return flow {
            val response = apiService.searchShows(query)
            val data = response.results
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        }.flowOn(ioDispatcher)
            .catch { emit(ApiResponse.Error(it.message.toString())) }
    }
}

