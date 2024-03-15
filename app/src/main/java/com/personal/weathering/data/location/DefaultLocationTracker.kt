package com.personal.weathering.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.personal.weathering.R
import com.personal.weathering.domain.location.LocationTracker
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.util.UiText
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume

class DefaultLocationTracker(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): LocationTracker {
    override suspend fun getCurrentLocation(): Resource<Location> {
        try {
            val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission)
                return Resource.Error(UiText.StringResource(R.string.permission_error).asString(application))

            val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!isGpsEnabled)
                return Resource.Error(UiText.StringResource(R.string.gps_error).asString(application))

            return suspendCancellableCoroutine { cont ->
                locationClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            cont.resume(Resource.Success(result))
                        } else {
                            cont.resume(Resource.Error(UiText.StringResource(R.string.location_error).asString(application)))
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener { location ->
                        cont.resume(Resource.Success(location))
                    }
                    addOnFailureListener {
                        cont.resume(Resource.Error(UiText.StringResource(R.string.location_error).asString(application)))
                    }
                    addOnCanceledListener {
                        cont.cancel(CancellationException("Location request cancelled"))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(UiText.StringResource(R.string.fetching_location_error).asString(application))
        }
    }
}