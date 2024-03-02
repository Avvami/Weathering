package com.personal.weathering.domain.repository

import com.personal.weathering.data.local.FavoriteEntity
import com.personal.weathering.data.local.PreferencesEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun deleteFavorite(favorite: FavoriteEntity)

    fun getFavorites(): Flow<List<FavoriteEntity>>

    fun getPreferences(): Flow<PreferencesEntity>

    suspend fun setCurrentCity(city: String, lat: Double, lon: Double)

    suspend fun setSearchLanguageCode(languageCode: String)

    suspend fun setUseCelsius(useCelsius: String)

    suspend fun setUseKmPerHour(useKmPerHour: String)

    suspend fun setUseHpa(useHpa: String)

    suspend fun setUseUSaq(useUSaq: String)
}