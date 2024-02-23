package com.personal.weathering.domain.models.weather

sealed class WindDirectionType(
    val direction: String
) {
    data object N: WindDirectionType(
        direction = "N"
    )
    data object NE: WindDirectionType(
        direction = "NE"
    )
    data object E: WindDirectionType(
        direction = "E"
    )
    data object SE: WindDirectionType(
        direction = "SE"
    )
    data object S: WindDirectionType(
        direction = "S"
    )
    data object SW: WindDirectionType(
        direction = "SW"
    )
    data object W: WindDirectionType(
        direction = "W"
    )

    data object NW: WindDirectionType(
        direction = "NW"
    )

    companion object {
        fun fromDegree(degree: Int): WindDirectionType {
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
