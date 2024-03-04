package com.personal.weathering.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.data.local.FavoriteEntity
import com.personal.weathering.domain.location.LocationTracker
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.repository.AqRepository
import com.personal.weathering.domain.repository.LocalRepository
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.MessageDialogState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val aqRepository: AqRepository,
    private val locationTracker: LocationTracker,
    private val localRepository: LocalRepository
): ViewModel() {

    var pullToRefreshState by mutableStateOf(PullToRefreshState(150f))

    var weatherState by mutableStateOf(WeatherState())
        private set

    var aqState by mutableStateOf(AqState())
        private set

    var messageDialogState by mutableStateOf(MessageDialogState())
        private set

    var holdSplash by mutableStateOf(true)
        private set

    private val _currentCityState = MutableStateFlow(CurrentCityState())
    val currentCityState: StateFlow<CurrentCityState> = _currentCityState.asStateFlow()

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    private val _favoritesState = MutableStateFlow<List<FavoritesState>>(listOf())
    val favoritesState: StateFlow<List<FavoritesState>> = _favoritesState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.getPreferences()
                .distinctUntilChangedBy { listOf(it.cityId, it.lat, it.lon, it.currentCity) }
                .collect { preferencesEntity ->
                    _currentCityState.update {
                        it.copy(
                            cityId = preferencesEntity.cityId,
                            name = preferencesEntity.currentCity,
                            lat = preferencesEntity.lat,
                            lon = preferencesEntity.lon
                        )
                    }
                    uiEvent(UiEvent.LoadWeatherInfo(preferencesEntity.lat, preferencesEntity.lon))
                    holdSplash = false
                }
        }
        viewModelScope.launch {
            localRepository.getPreferences()
                .distinctUntilChangedBy {
                    listOf(it.searchLanguageCode, it.useLocation, it.useCelsius, it.useKmPerHour, it.useHpa, it.useUSaq)
                }
                .collect { preferencesEntity ->
                    _preferencesState.update {
                        it.copy(
                            searchLanguageCode = preferencesEntity.searchLanguageCode,
                            useLocation = preferencesEntity.useLocation,
                            useCelsius = preferencesEntity.useCelsius,
                            useKmPerHour = preferencesEntity.useKmPerHour,
                            useHpa = preferencesEntity.useHpa,
                            useUSaq = preferencesEntity.useUSaq,
                        )
                    }
                }
        }
        viewModelScope.launch {
            localRepository.getFavorites().collect { favoritesEntity ->
                _favoritesState.value = favoritesEntity.map {
                    FavoritesState(it.id, it.cityId, it.city, it.lat, it.lon)
                }
            }
        }
    }

    private fun loadWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherState = weatherState.copy(
                isLoading = true,
                error = null
            )

            var weatherInfo: WeatherInfo? = null
            var weatherError: String? = null

            weatherRepository.getWeatherData(lat, lon).let { result ->
                when (result) {
                    is Resource.Error -> {
                        weatherError = result.message
                    }
                    is Resource.Success -> {
                        weatherInfo = result.data
                    }
                }
            }

            weatherState = weatherState.copy(
                weatherInfo = weatherInfo,
                isLoading = false,
                error = weatherError
            )
            pullToRefreshState.endRefresh()

            aqState = aqState.copy(
                isLoading = true,
                error = null
            )

            var aqInfo: AqInfo? = null
            var aqError: String? = null

            aqRepository.getAqData(lat, lon).let { result ->
                when (result) {
                    is Resource.Error -> {
                        aqError = result.message
                    }
                    is Resource.Success -> {
                        aqInfo = result.data
                    }
                }
            }

            aqState = aqState.copy(
                aqInfo = aqInfo,
                isLoading = false,
                error = aqError
            )

            locationTracker.getCurrentLocation()?.let {} ?: kotlin.run {}
        }
    }

    private fun loadAqInfo(lat: Double, lon: Double) {
        viewModelScope.launch {
            aqState = aqState.copy(
                isLoading = true,
                error = null
            )

            var aqInfo: AqInfo? = null
            var error: String? = null

            aqRepository.getAqData(lat, lon).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        aqInfo = result.data
                    }
                }
            }

            aqState = aqState.copy(
                aqInfo = aqInfo,
                isLoading = false,
                error = error
            )
            pullToRefreshState.endRefresh()
        }
    }

    fun uiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.LoadWeatherInfo -> {
                loadWeatherInfo(event.lat, event.lon)
            }
            is UiEvent.ShowMessageDialog -> {
                messageDialogState = messageDialogState.copy(
                    isShown = true,
                    iconRes = event.iconRes,
                    titleRes = event.titleRes,
                    messageRes = event.messageRes,
                    messageString = event.messageString,
                    dismissTextRes = event.dismissTextRes,
                    onDismissRequest = { uiEvent(UiEvent.CloseMessageDialog) },
                    onDismiss = event.onDismiss,
                    confirmTextRes = event.confirmTextRes,
                    onConfirm = event.onConfirm ?: { uiEvent(UiEvent.CloseMessageDialog) }
                )
            }
            UiEvent.CloseMessageDialog -> { messageDialogState = messageDialogState.copy(isShown = false) }
            is UiEvent.SetCurrentCityState -> {
                viewModelScope.launch {
                    localRepository.setCurrentCity(event.cityId, event.city, event.lat, event.lon)
                }
            }
            is UiEvent.SetSearchLanguage -> {
                viewModelScope.launch {
                    localRepository.setSearchLanguageCode(event.code)
                }
            }
            is UiEvent.SetUseLocation -> {
                viewModelScope.launch {
                    localRepository.setUseLocation(event.useLocation)
                }
            }
            is UiEvent.SetTemperatureUnit -> {
                viewModelScope.launch {
                    localRepository.setUseCelsius(event.useCelsius)
                }
            }
            is UiEvent.SetSpeedUnit -> {
                viewModelScope.launch {
                    localRepository.setUseKmPerHour(event.useKmPerHour)
                }
            }
            is UiEvent.SetPressureUnit -> {
                viewModelScope.launch {
                    localRepository.setUseHpa(event.useHpa)
                }
            }
            is UiEvent.SetAqiUnit -> {
                viewModelScope.launch {
                    localRepository.setUseUSaq(event.useUSaq)
                }
            }
            is UiEvent.UpdateAqInfo -> {
                loadAqInfo(event.lat, event.lon)
            }
            is UiEvent.AddFavorite -> {
                viewModelScope.launch {
                    localRepository.addFavorite(
                        FavoriteEntity(cityId = event.cityId, city = event.city, lat = event.lat, lon = event.lon)
                    )
                }
            }
            is UiEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    localRepository.removeFavorite(
                        FavoriteEntity(id = event.id, cityId = event.cityId, city = event.city, lat = event.lat, lon = event.lon)
                    )
                }
            }
        }
    }
}