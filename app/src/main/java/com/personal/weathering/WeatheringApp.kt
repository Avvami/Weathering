package com.personal.weathering

import android.app.Application
import com.personal.weathering.core.di.AppModule
import com.personal.weathering.core.di.AppModuleImpl

class WeatheringApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}