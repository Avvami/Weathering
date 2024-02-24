package com.personal.weathering.domain.models.weather

data class WeatherInfo(
    val currentWeatherData: CurrentWeatherData,
    val twentyFourHoursWeatherData: List<TwentyFourHoursWeatherData>,
    val hourlyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val dailyWeatherData: Map<Int, List<DailyWeatherData>>
)
