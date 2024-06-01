package com.personal.weathering.weather.domain.models

data class WeatherInfo(
    val currentWeatherData: CurrentWeatherData,
    val twentyFourHoursWeatherData: List<TwentyFourHoursWeatherData>,
    val hourlyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val dailyWeatherData: List<DailyWeatherData>,
    val dailyWeatherSummaryData: Map<Int, List<DailyWeatherSummaryData>>
)
