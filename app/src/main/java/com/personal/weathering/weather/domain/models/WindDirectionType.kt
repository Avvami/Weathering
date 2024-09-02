package com.personal.weathering.weather.domain.models

import androidx.annotation.StringRes
import com.personal.weathering.R

sealed class WindDirectionType(
    @StringRes val directionRes: Int
) {
    data object N: WindDirectionType(
        directionRes = R.string.north
    )
    data object NE: WindDirectionType(
        directionRes = R.string.north_east
    )
    data object E: WindDirectionType(
        directionRes = R.string.east
    )
    data object SE: WindDirectionType(
        directionRes = R.string.south_east
    )
    data object S: WindDirectionType(
        directionRes = R.string.south
    )
    data object SW: WindDirectionType(
        directionRes = R.string.south_west
    )
    data object W: WindDirectionType(
        directionRes = R.string.west
    )

    data object NW: WindDirectionType(
        directionRes = R.string.north_west
    )

    companion object {
        fun fromDegree(degree: Int?): WindDirectionType {
            return when(degree) {
                in 0 until 23 -> N
                in 23 until 67 -> NW
                in 67 until 112 -> W
                in 112 until 157 -> SW
                in 157 until 202 -> S
                in 202 until 247 -> SE
                in 247 until 292 -> E
                in 292 until 337 -> NE
                in 337 until 361 -> N
                else -> N
            }
        }
    }
}
