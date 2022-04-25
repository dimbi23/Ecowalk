package com.wanowanconsult.ecowalk.presentation.activity

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.framework.manager.LocationProvider
import com.wanowanconsult.ecowalk.framework.manager.PermissionManager
import com.wanowanconsult.ecowalk.framework.manager.StepCounterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewmodel @Inject constructor(
    private val repository: ActivityRepository,
    private val locationProvider: LocationProvider,
    private val stepCounterManager: StepCounterManager
) : ViewModel(), PermissionManager {

    var state by mutableStateOf(ActivityState())
    var locations = locationProvider.liveLocation
    var step = stepCounterManager.liveSteps

    override val isPermissionRequestButtonClicked: MutableState<Boolean> = mutableStateOf(false)

    override fun onPermissionGranted() {
        locationProvider.getUserLocation()
        locationProvider.liveLocation.observeForever {
            Log.d("ActivityViewmodel", "Lat: ${it.first}, Long: ${it.second}")
        }
    }

    fun startTracking(){
        locationProvider.trackUser()
        stepCounterManager.setupStepCounter()
    }

    fun stopTracking(){
        locationProvider.stopTracking()
        stepCounterManager.unloadStepCounter()
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.OnRequestPermissionsButtonClick -> {
                //requestPermissions()
            }
        }
    }
}