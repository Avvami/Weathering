package com.personal.weathering.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {

    @Query("SELECT * FROM preferencesentity")
    fun getPreferences(): Flow<PreferencesEntity>

    @Query("UPDATE preferencesentity SET currentCity = :city, lat = :lat, lon = :lon")
    suspend fun setCurrentCity(city: String, lat: Double, lon: Double)

    @Query("UPDATE preferencesentity SET searchLanguageCode = :languageCode")
    suspend fun setSearchLanguageCode(languageCode: String)

    @Query("UPDATE preferencesentity SET useCelsius = :useCelsius")
    suspend fun setUseCelsius(useCelsius: String)

    @Query("UPDATE preferencesentity SET useKmPerHour = :useKmPerHour")
    suspend fun setUseKmPerHour(useKmPerHour: String)

    @Query("UPDATE preferencesentity SET useHpa = :useHpa")
    suspend fun setUseHpa(useHpa: String)

    @Query("UPDATE preferencesentity SET useUSaq = :useUSaq")
    suspend fun setUseUSaq(useUSaq: String)
}