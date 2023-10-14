package com.personal.weatherapp.domain.weather

sealed class WeatherWindDirection(
    val direction: String
) {
    object N: WeatherWindDirection(
        direction = "N"
    )
    object NE: WeatherWindDirection(
        direction = "NE"
    )
    object E: WeatherWindDirection(
        direction = "E"
    )
    object SE: WeatherWindDirection(
        direction = "SE"
    )
    object S: WeatherWindDirection(
        direction = "S"
    )
    object SW: WeatherWindDirection(
        direction = "SW"
    )
    object W: WeatherWindDirection(
        direction = "W"
    )
    object NW: WeatherWindDirection(
        direction = "NW"
    )

    companion object {
        fun fromDegree(degree: Int): WeatherWindDirection {
            return when(degree) {
                in 0 until 23 -> N
                in 23 until 67 -> NE
                in 67 until 112 -> E
                in 112 until 157 -> SE
                in 157 until 202 -> S
                in 202 until 247 -> SW
                in 247 until 292 -> W
                in 292 until 337 -> NW
                in 337 until 361 -> N
                else -> N
            }
        }
    }
}
