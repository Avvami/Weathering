package com.personal.weathering.di

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.personal.weathering.data.location.DefaultLocationTracker
import com.personal.weathering.data.remote.AqApi
import com.personal.weathering.data.remote.WeatherApi
import com.personal.weathering.data.repository.AqRepositoryImpl
import com.personal.weathering.data.repository.WeatherRepositoryImpl
import com.personal.weathering.domain.location.LocationTracker
import com.personal.weathering.domain.repository.AqRepository
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.C
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

interface AppModule {
    val weatherApi: WeatherApi
    val weatherRepository: WeatherRepository
    val aqApi: AqApi
    val aqRepository: AqRepository
    val locationTracker: LocationTracker
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

    override val locationTracker: LocationTracker by lazy {
        DefaultLocationTracker(
            LocationServices.getFusedLocationProviderClient(appContext),
            appContext as Application
        )
    }
}