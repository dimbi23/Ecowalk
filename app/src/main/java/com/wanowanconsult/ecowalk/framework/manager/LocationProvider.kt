package com.wanowanconsult.ecowalk.framework.manager

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.wanowanconsult.ecowalk.EcowalkApplication

class LocationProvider {
    private val client
            by lazy { LocationServices.getFusedLocationProviderClient(EcowalkApplication.getApplicationContext()) }
    private val locations = mutableListOf<Pair<Double, Double>>()
    val liveLocation = MutableLiveData<Pair<Double, Double>>()
    val liveLocations = MutableLiveData<List<Pair<Double, Double>>>()
    val liveDistance = MutableLiveData<Int>()
    var distance: Int = 0

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val currentLocation = result.lastLocation
            val latLng = Pair(first = currentLocation.latitude, second = currentLocation.longitude)
            val lastLocation = locations.lastOrNull()

            /*if (lastLocation != null) {
                distance += SphericalUtil.computeDistanceBetween(lastLocation, latLng).roundToInt()
                liveDistance.value = distance
            }*/

            locations.add(latLng)
            liveLocations.value = locations
        }
    }

    @SuppressLint("MissingPermission")
    fun trackUser() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000

        client.requestLocationUpdates(locationRequest, locationCallback,
            Looper.getMainLooper())
    }

    fun stopTracking() {
        client.removeLocationUpdates(locationCallback)
        locations.clear()
        distance = 0
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation() {
        client.lastLocation.addOnSuccessListener { location ->
            val latLng = Pair(first = location.latitude, second = location.longitude)
            locations.add(latLng)
            liveLocation.value = latLng
        }
    }
}
