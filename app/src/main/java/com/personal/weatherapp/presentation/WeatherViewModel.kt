package com.personal.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.location.LocationTracker
import com.personal.weatherapp.domain.repository.AqRepository
import com.personal.weatherapp.domain.repository.WeatherRepository
import com.personal.weatherapp.domain.util.Resource
import com.personal.weatherapp.domain.weather.WeatherInfo
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val aqRepository: AqRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                weatherError = null,
                aqError = null
            )

            var weatherInfo: WeatherInfo? = null
            var aqInfo: AQInfo? = null
            var weatherError: String? = null
            var aqError: String? = null

            locationTracker.getCurrentLocation()?.let { location ->
                weatherRepository.getWeatherData(location.latitude, location.longitude).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            weatherError = result.message
                        }
                        is Resource.Success -> {
                            weatherInfo = result.data
                        }
                    }
                }

                aqRepository.getAQData(location.latitude, location.longitude).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            aqError = result.message
                        }
                        is Resource.Success -> {
                            aqInfo = result.data
                        }
                    }
                }

                state = state.copy(
                    weatherInfo = weatherInfo,
                    aqInfo = aqInfo,
                    isLoading = false,
                    weatherError = weatherError,
                    aqError = aqError
                )
            } ?: kotlin.run {
                weatherRepository.getWeatherData(56.0184, 92.8672).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            weatherError = result.message
                        }
                        is Resource.Success -> {
                            weatherInfo = result.data
                        }
                    }
                }

                aqRepository.getAQData(56.0184, 92.8672).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            aqError = result.message
                        }
                        is Resource.Success -> {
                            aqInfo = result.data
                        }
                    }
                }

                state = state.copy(
                    weatherInfo = weatherInfo,
                    aqInfo = aqInfo,
                    isLoading = false,
                    weatherError = weatherError,
                    aqError = aqError
                )
            }
        }
    }

    fun uiEvent(event: UIEvent) {
        when(event) {
            is UIEvent.OpenAlertDialog -> {
                state = state.copy(
                    openAlertDialog = event.isOpen
                )
            }
            UIEvent.LoadWeatherInfo -> {
                loadWeatherInfo()
            }
            is UIEvent.ChangeAccentColors -> {
                state = state.copy(
                    surfaceColor = event.surfaceColor,
                    onSurfaceColor = event.onSurfaceColor,
                    plainTextColor = event.plainTextColor
                )
            }
        }
    }
}