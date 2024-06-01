package com.personal.weathering.core.util

import com.personal.weathering.R

interface PermissionProvider {
    fun getIcon(): Int
    fun getDescription(isPermanentlyDeclined: Boolean): Int
}

class LocationPermissionProvider: PermissionProvider {
    override fun getIcon(): Int {
        return R.drawable.icon_my_location_fill1_wght400
    }

    override fun getDescription(isPermanentlyDeclined: Boolean): Int {
        return if (isPermanentlyDeclined) {
            R.string.location_permission_declined
        } else {
            R.string.location_permission
        }
    }
}