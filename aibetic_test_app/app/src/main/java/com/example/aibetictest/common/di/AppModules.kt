package com.example.aibetictest.common.di

import com.example.aibetictest.common.net.AuthInterceptor
import com.example.aibetictest.common.net.ExchangeRatesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.*
import retrofit2.Retrofit

const val NAME_LOCAL_DATA_SOURCE = "LocalDataSource"
const val NAME_REMOTE_DATA_SOURCE = "RemoteDataSource"
const val NAME_DISPATCHER_MAIN = "MainDispatcher"
const val NAME_DISPATCHER_IO = "IoDispatcher"

@Module(includes = [NetworkModule::class])
@ComponentScan("com.example.aibetictest")
class MainModule {
    @Factory
    fun provideClock(): Clock = Clock.System

    @Single
    @Named(NAME_DISPATCHER_MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Single
    @Named(NAME_DISPATCHER_IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
class NetworkModule {
    @Single
    @OptIn(ExperimentalSerializationApi::class)
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

    @Single
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi {
        return retrofit.create(ExchangeRatesApi::class.java)
    }

    @Single
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}