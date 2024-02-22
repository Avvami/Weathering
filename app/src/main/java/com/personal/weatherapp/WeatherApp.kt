package com.personal.weatherapp

import android.app.Application
import com.personal.weatherapp.di.AppModule
import com.personal.weatherapp.di.AppModuleImpl

class WeatherApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}