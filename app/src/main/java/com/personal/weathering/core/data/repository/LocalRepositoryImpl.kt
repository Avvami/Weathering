package com.personal.weathering.core.data.repository

import com.personal.weathering.core.data.local.FavoriteEntity
import com.personal.weathering.core.data.local.FavoritesDao
import com.personal.weathering.core.data.local.PreferencesDao
import com.personal.weathering.core.data.local.PreferencesEntity
import com.personal.weathering.core.data.local.SearchHistoryDao
import com.personal.weathering.core.data.local.SearchResultEntity
import com.personal.weathering.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val preferencesDao: PreferencesDao,
    private val favoritesDao: FavoritesDao,
    private val searchHistoryDao: SearchHistoryDao
): LocalRepository {
    override suspend fun addFavorite(favorite: FavoriteEntity) = favoritesDao.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: FavoriteEntity) = favoritesDao.removeFavorite(favorite)

    override fun getFavorites(): Flow<List<FavoriteEntity>> = favoritesDao.getFavorites()

    override fun getPreferences(): Flow<PreferencesEntity> = preferencesDao.getPreferences()

    override suspend fun setSelectedCity(cityId: Int, city: String, lat: Double, lon: Double) = preferencesDao.setSelectedCity(cityId, city, lat, lon)
    override suspend fun setCurrentLocationCity(city: String, lat: Double, lon: Double) = preferencesDao.setCurrentLocationCity(city, lat, lon)

    override suspend fun setSearchLanguageCode(languageCode: String) = preferencesDao.setSearchLanguageCode(languageCode)

    override suspend fun setDarkMode(isDark: Boolean) = preferencesDao.setDarkMode(isDark)

    override suspend fun setUseLocation(useLocation: Boolean) = preferencesDao.setUseLocation(useLocation)

    override suspend fun setUseCelsius(useCelsius: Boolean) = preferencesDao.setUseCelsius(useCelsius)

    override suspend fun setUseKmPerHour(useKmPerHour: Boolean) = preferencesDao.setUseKmPerHour(useKmPerHour)

    override suspend fun setUseHpa(useHpa: Boolean) = preferencesDao.setUseHpa(useHpa)

    override suspend fun setUseUSaq(useUSaq: Boolean) = preferencesDao.setUseUSaq(useUSaq)
    override suspend fun setUse12hour(use12hour: Boolean) = preferencesDao.setUse12hour(use12hour)

    override suspend fun addToHistory(searchResult: SearchResultEntity) = searchHistoryDao.addToHistory(searchResult)

    override suspend fun removeFromHistory(searchResult: SearchResultEntity) = searchHistoryDao.removeFromHistory(searchResult)

    override fun getSearchHistory(): Flow<List<SearchResultEntity>> = searchHistoryDao.getSearchHistory()
}