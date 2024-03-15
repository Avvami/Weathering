package com.personal.weathering.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {

    @Query("SELECT * FROM preferencesentity")
    fun getPreferences(): Flow<PreferencesEntity>

    @Query("UPDATE preferencesentity SET useLocation = 0, cityId = :cityId, selectedCity = :city, selectedCityLat = :lat, selectedCityLon = :lon")
    suspend fun setSelectedCity(cityId: Int, city: String, lat: Double, lon: Double)

    @Query("UPDATE preferencesentity SET currentLocationCity = :city, currentLocationLat = :lat, currentLocationLon = :lon")
    suspend fun setCurrentLocationCity(city: String, lat: Double, lon: Double)

    @Query("UPDATE preferencesentity SET searchLanguageCode = :languageCode")
    suspend fun setSearchLanguageCode(languageCode: String)

    @Query("UPDATE preferencesentity SET isDark = :isDark")
    suspend fun setDarkMode(isDark: Boolean)

    @Query("UPDATE preferencesentity SET useLocation = :useLocation")
    suspend fun setUseLocation(useLocation: Boolean)

    @Query("UPDATE preferencesentity SET useCelsius = :useCelsius")
    suspend fun setUseCelsius(useCelsius: Boolean)

    @Query("UPDATE preferencesentity SET useKmPerHour = :useKmPerHour")
    suspend fun setUseKmPerHour(useKmPerHour: Boolean)

    @Query("UPDATE preferencesentity SET useHpa = :useHpa")
    suspend fun setUseHpa(useHpa: Boolean)

    @Query("UPDATE preferencesentity SET useUSaq = :useUSaq")
    suspend fun setUseUSaq(useUSaq: Boolean)
}