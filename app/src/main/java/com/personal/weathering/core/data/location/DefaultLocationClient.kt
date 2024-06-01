package com.personal.weathering.core.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.personal.weathering.R
import com.personal.weathering.core.data.mappers.toLocationInfo
import com.personal.weathering.core.domain.location.LocationClient
import com.personal.weathering.core.domain.models.LocationInfo
import com.personal.weathering.core.util.Resource
import com.personal.weathering.core.util.UiText
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultLocationClient(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): LocationClient {
    override suspend fun getCurrentLocation(): Resource<LocationInfo> {
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
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnabled && !isNetworkEnabled)
                return Resource.Error(UiText.StringResource(R.string.gps_error).asString(application))

            return suspendCancellableCoroutine { cont ->
                locationClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        cont.resume(Resource.Success(task.result.toLocationInfo()))
                    } else {
                        val cancellationTokenSource = CancellationTokenSource()
                        locationClient.getCurrentLocation(
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                            cancellationTokenSource.token
                        ).addOnCompleteListener { newLocationTask ->
                            if (newLocationTask.isSuccessful && newLocationTask.result != null) {
                                cont.resume(Resource.Success(newLocationTask.result.toLocationInfo()))
                            } else {
                                cont.resume(Resource.Error(UiText.StringResource(R.string.location_error).asString(application)))
                            }
                        }.addOnFailureListener { exception ->
                            cont.resumeWithException(exception)
                        }
                        cont.invokeOnCancellation {
                            cancellationTokenSource.cancel()
                        }
                    }
                }.addOnFailureListener { exception ->
                    cont.resumeWithException(exception)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(UiText.StringResource(R.string.fetching_location_error).asString(application))
        }
    }
}