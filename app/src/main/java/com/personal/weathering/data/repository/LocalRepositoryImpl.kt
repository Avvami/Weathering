package com.personal.weathering.data.repository

import com.personal.weathering.data.local.FavoriteEntity
import com.personal.weathering.data.local.FavoritesDao
import com.personal.weathering.data.local.PreferencesDao
import com.personal.weathering.data.local.PreferencesEntity
import com.personal.weathering.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val preferencesDao: PreferencesDao,
    private val favoritesDao: FavoritesDao
): LocalRepository {
    override suspend fun insertFavorite(favorite: FavoriteEntity) = favoritesDao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: FavoriteEntity) = favoritesDao.deleteFavorite(favorite)

    override fun getFavorites(): Flow<List<FavoriteEntity>> = favoritesDao.getFavorites()

    override fun getPreferences(): Flow<PreferencesEntity> = preferencesDao.getPreferences()

    override suspend fun setCurrentCity(city: String, lat: Double, lon: Double) = preferencesDao.setCurrentCity(city, lat, lon)

    override suspend fun setSearchLanguageCode(languageCode: String) = preferencesDao.setSearchLanguageCode(languageCode)

    override suspend fun setUseCelsius(useCelsius: String) = preferencesDao.setUseCelsius(useCelsius)

    override suspend fun setUseKmPerHour(useKmPerHour: String) = preferencesDao.setUseKmPerHour(useKmPerHour)

    override suspend fun setUseHpa(useHpa: String) = preferencesDao.setUseHpa(useHpa)

    override suspend fun setUseUSaq(useUSaq: String) = preferencesDao.setUseUSaq(useUSaq)
}