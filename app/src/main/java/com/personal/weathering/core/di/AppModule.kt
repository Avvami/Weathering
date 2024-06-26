package com.personal.weathering.core.di

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.personal.weathering.core.data.local.AppDatabase
import com.personal.weathering.core.data.location.DefaultLocationClient
import com.personal.weathering.aq.data.remote.AqApi
import com.personal.weathering.core.data.remote.GeocodingApi
import com.personal.weathering.search.data.remote.SearchApi
import com.personal.weathering.weather.data.remote.WeatherApi
import com.personal.weathering.aq.data.repository.AqRepositoryImpl
import com.personal.weathering.core.data.repository.ConnectivityRepositoryImpl
import com.personal.weathering.core.data.repository.GeocodingRepositoryImpl
import com.personal.weathering.core.data.repository.LocalRepositoryImpl
import com.personal.weathering.search.data.repository.SearchRepositoryImpl
import com.personal.weathering.weather.data.repository.WeatherRepositoryImpl
import com.personal.weathering.core.domain.location.LocationClient
import com.personal.weathering.aq.domain.repository.AqRepository
import com.personal.weathering.core.domain.repository.ConnectivityRepository
import com.personal.weathering.core.domain.repository.GeocodingRepository
import com.personal.weathering.core.domain.repository.LocalRepository
import com.personal.weathering.search.domain.repository.SearchRepository
import com.personal.weathering.weather.domain.repository.WeatherRepository
import com.personal.weathering.core.util.C
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

interface AppModule {
    val weatherApi: WeatherApi
    val weatherRepository: WeatherRepository
    val aqApi: AqApi
    val aqRepository: AqRepository
    val locationClient: LocationClient
    val searchApi: SearchApi
    val searchRepository: SearchRepository
    val localRepository: LocalRepository
    val geocodingApi: GeocodingApi
    val geocodingRepository: GeocodingRepository
    val connectivityRepository: ConnectivityRepository
}

class AppModuleImpl(private val appContext: Context): AppModule {

    override val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(C.OM_WEATHER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    override val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherApi, appContext)
    }

    override val aqApi: AqApi by lazy {
        Retrofit.Builder()
            .baseUrl(C.OM_AQ_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    override val aqRepository: AqRepository by lazy {
        AqRepositoryImpl(aqApi, appContext)
    }

    override val locationClient: LocationClient by lazy {
        DefaultLocationClient(
            LocationServices.getFusedLocationProviderClient(appContext),
            appContext as Application
        )
    }

    override val searchApi: SearchApi by lazy {
        Retrofit.Builder()
            .baseUrl(C.OM_SEARCH_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    override val searchRepository: SearchRepository by lazy {
        SearchRepositoryImpl(searchApi, appContext)
    }

    override val localRepository: LocalRepository by lazy {
        LocalRepositoryImpl(
            preferencesDao = AppDatabase.getDatabase(appContext).preferencesDao,
            favoritesDao = AppDatabase.getDatabase(appContext).favoritesDao,
            searchHistoryDao = AppDatabase.getDatabase(appContext).searchHistoryDao
        )
    }

    override val geocodingApi: GeocodingApi by lazy {
        Retrofit.Builder()
            .baseUrl(C.NOMINATIM_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    override val geocodingRepository: GeocodingRepository by lazy {
        GeocodingRepositoryImpl(geocodingApi, appContext)
    }

    override val connectivityRepository: ConnectivityRepository by lazy {
        ConnectivityRepositoryImpl(appContext)
    }
}