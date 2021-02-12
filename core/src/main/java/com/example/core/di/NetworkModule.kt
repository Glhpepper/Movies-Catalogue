package com.example.core.di

import android.os.Build
import com.example.core.data.source.remote.network.ApiService
import com.example.core.utils.Constants.Companion.BASE_URL
import com.example.core.utils.Constants.Companion.HOSTNAME
import com.example.core.utils.Constants.Companion.OKHTTP_TIMEOUT
import com.example.core.utils.Constants.Companion.PIN_1
import com.example.core.utils.Constants.Companion.PIN_2
import com.example.core.utils.Constants.Companion.PIN_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            val hostname = HOSTNAME
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, PIN_1)
                .add(hostname, PIN_2)
                .add(hostname, PIN_3)
                .build()
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        } else {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}