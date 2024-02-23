package com.personal.weathering

import android.app.Application
import com.personal.weathering.di.AppModule
import com.personal.weathering.di.AppModuleImpl

class WeatheringApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}