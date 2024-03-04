package com.personal.weathering.domain.repository

import com.personal.weathering.data.local.FavoriteEntity
import com.personal.weathering.data.local.PreferencesEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun deleteFavorite(favorite: FavoriteEntity)

    fun getFavorites(): Flow<List<FavoriteEntity>>

    fun getPreferences(): Flow<PreferencesEntity>

    suspend fun setCurrentCity(cityId: Int, city: String, lat: Double, lon: Double)

    suspend fun setSearchLanguageCode(languageCode: String)

    suspend fun setUseCelsius(useCelsius: Boolean)

    suspend fun setUseKmPerHour(useKmPerHour: Boolean)

    suspend fun setUseHpa(useHpa: Boolean)

    suspend fun setUseUSaq(useUSaq: Boolean)
}