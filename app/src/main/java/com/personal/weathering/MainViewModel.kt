package com.personal.weathering

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.aq.domain.models.AqInfo
import com.personal.weathering.aq.domain.repository.AqRepository
import com.personal.weathering.aq.presentation.AqState
import com.personal.weathering.core.data.local.FavoriteEntity
import com.personal.weathering.core.domain.location.LocationClient
import com.personal.weathering.core.domain.repository.ConnectivityRepository
import com.personal.weathering.core.domain.repository.GeocodingRepository
import com.personal.weathering.core.domain.repository.LocalRepository
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.core.presentation.MessageDialogState
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.Resource
import com.personal.weathering.weather.domain.models.WeatherInfo
import com.personal.weathering.weather.domain.repository.WeatherRepository
import com.personal.weathering.weather.presenation.WeatherState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val aqRepository: AqRepository,
    private val locationClient: LocationClient,
    private val localRepository: LocalRepository,
    private val geocodingRepository: GeocodingRepository,
    private val connectivityRepository: ConnectivityRepository
): ViewModel() {

    init {
        observeNetworkChanges()
    }

    var isNetworkConnected by mutableStateOf(connectivityRepository.getCurrentNetworkStatus())
        private set

    private fun observeNetworkChanges() {
        connectivityRepository.observe().onEach { isAvailable ->
            isNetworkConnected = isAvailable
        }.launchIn(viewModelScope)
    }

    var pullToRefreshState by mutableStateOf(PullToRefreshState(150f))

    private val _locationError = Channel<String>()
    val locationError = _locationError.receiveAsFlow()

    var weatherState by mutableStateOf(WeatherState())
        private set

    var aqState by mutableStateOf(AqState())
        private set

    var messageDialogState by mutableStateOf(MessageDialogState())
        private set

    var holdSplash by mutableStateOf(true)
        private set

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    private val _favoritesState = MutableStateFlow<List<FavoritesState>>(listOf())
    val favoritesState: StateFlow<List<FavoritesState>> = _favoritesState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.getPreferences()
                .distinctUntilChangedBy { listOf(it.selectedCity, it.selectedCityLat, it.selectedCityLon) }
                .collect { preferencesEntity ->
                    _preferencesState.update {
                        it.copy(
                            selectedCity = preferencesEntity.selectedCity,
                            selectedCityLat = preferencesEntity.selectedCityLat,
                            selectedCityLon = preferencesEntity.selectedCityLon,
                            useLocation = preferencesEntity.useLocation,
                            currentLocationCity = preferencesEntity.currentLocationCity,
                            currentLocationLat = preferencesEntity.currentLocationLat,
                            currentLocationLon = preferencesEntity.currentLocationLon,
                        )
                    }
                    uiEvent(
                        UiEvent.LoadWeatherInfo(
                            preferencesEntity.useLocation,
                            preferencesEntity.selectedCityLat,
                            preferencesEntity.selectedCityLon
                        )
                    )
                    holdSplash = false
                }
        }
        viewModelScope.launch {
            localRepository.getPreferences()
                .distinctUntilChangedBy {
                    listOf(it.useLocation, it.searchLanguageCode, it.isDark, it.useCelsius, it.useKmPerHour, it.useHpa, it.useUSaq, it.use12hour)
                }
                .collect { preferencesEntity ->
                    _preferencesState.update {
                        it.copy(
                            useLocation = preferencesEntity.useLocation,
                            searchLanguageCode = preferencesEntity.searchLanguageCode,
                            isDark = preferencesEntity.isDark,
                            useCelsius = preferencesEntity.useCelsius,
                            useKmPerHour = preferencesEntity.useKmPerHour,
                            useHpa = preferencesEntity.useHpa,
                            useUSaq = preferencesEntity.useUSaq,
                            use12hour = preferencesEntity.use12hour
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

    private fun loadWeatherInfo(useLocation: Boolean, lat: Double, lon: Double) {
        if (!isNetworkConnected) {
            pullToRefreshState.endRefresh()
            return
        }
        if (preferencesState.value.selectedCity == "" && !useLocation) return
        viewModelScope.launch {
            weatherState = weatherState.copy(
                isLoading = true,
                error = null
            )

            var weatherInfo: WeatherInfo? = weatherState.weatherInfo
            var weatherError: String? = null

            if (useLocation) {
                weatherState = weatherState.copy(
                    retrievingLocation = true
                )
                locationClient.getCurrentLocation().let { locationResult ->
                    when (locationResult) {
                        is Resource.Error -> {
                            _locationError.send(locationResult.message ?: "")
                            localRepository.setSelectedCity(0, "", 0.0, 0.0)
                            localRepository.setUseLocation(true)
                            weatherRepository.getWeatherData(preferencesState.value.currentLocationLat, preferencesState.value.currentLocationLon).let { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        weatherError = result.message
                                    }
                                    is Resource.Success -> {
                                        weatherInfo = result.data
                                    }
                                }
                            }
                            launch {
                                loadAqInfo(lat, lon)
                            }
                        }
                        is Resource.Success -> {
                            locationResult.data?.let { locationInfo ->
                                geocodingRepository.getCurrentLocation(locationInfo.latitude, locationInfo.longitude).let { result ->
                                    when (result) {
                                        is Resource.Error -> {
                                            _locationError.send(result.message ?: "")
                                        }
                                        is Resource.Success -> {
                                            result.data?.error?.let { _locationError.send(it) }
                                            val firstNonNull = listOf(
                                                result.data?.city,
                                                result.data?.town,
                                                result.data?.village,
                                                result.data?.hamlet,
                                                result.data?.municipality
                                            ).firstOrNull { it != null }
                                            localRepository.setCurrentLocationCity(
                                                city = firstNonNull ?: "???",
                                                lat = locationInfo.latitude,
                                                lon = locationInfo.longitude
                                            )
                                        }
                                    }
                                }
                                localRepository.setSelectedCity(0, "", 0.0, 0.0)
                                weatherRepository.getWeatherData(locationInfo.latitude, locationInfo.longitude).let { result ->
                                    when (result) {
                                        is Resource.Error -> {
                                            weatherError = result.message
                                        }
                                        is Resource.Success -> {
                                            weatherInfo = result.data
                                        }
                                    }
                                }
                                localRepository.setUseLocation(true)
                                launch {
                                    loadAqInfo(locationInfo.latitude, locationInfo.longitude)
                                }
                            } ?: _locationError.send("An unexpected error occurred")
                        }
                    }
                }
            } else {
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
                launch {
                    loadAqInfo(lat, lon)
                }
            }

            weatherState = weatherState.copy(
                weatherInfo = weatherInfo,
                retrievingLocation = false,
                isLoading = false,
                error = weatherError
            )
            if (pullToRefreshState.isRefreshing) pullToRefreshState.endRefresh()
        }
    }

    private fun loadAqInfo(lat: Double, lon: Double) {
        if (!isNetworkConnected && pullToRefreshState.isRefreshing) {
            pullToRefreshState.endRefresh()
            return
        }
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
            if (pullToRefreshState.isRefreshing) pullToRefreshState.endRefresh()
        }
    }

    fun uiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.LoadWeatherInfo -> {
                loadWeatherInfo(event.useLocation, event.lat, event.lon)
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
            is UiEvent.SetSelectedCity -> {
                viewModelScope.launch {
                    localRepository.setUseLocation(false)
                    localRepository.setSelectedCity(event.cityId, event.city, event.lat, event.lon)
                }
            }
            is UiEvent.SetSearchLanguage -> {
                viewModelScope.launch {
                    localRepository.setSearchLanguageCode(event.code)
                }
            }
            is UiEvent.SetDarkMode -> {
                viewModelScope.launch {
                    localRepository.setDarkMode(event.isDark)
                }
            }
            is UiEvent.SetUseLocation -> {
                loadWeatherInfo(true, 0.0, 0.0)
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
            is UiEvent.SetTimeFormat -> {
                viewModelScope.launch {
                    localRepository.setUse12hour(event.use12hour)
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