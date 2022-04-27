package com.wanowanconsult.ecowalk.presentation.activity

import android.os.Handler
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanowanconsult.ecowalk.domain.model.Activity
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.framework.event.StartActivityEvent
import com.wanowanconsult.ecowalk.framework.event.StartServiceEvent
import com.wanowanconsult.ecowalk.framework.event.StopActivityEvent
import com.wanowanconsult.ecowalk.framework.manager.LocationProvider
import com.wanowanconsult.ecowalk.framework.manager.PermissionManager
import com.wanowanconsult.ecowalk.framework.manager.StepCounterManager
import com.wanowanconsult.ecowalk.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.intellij.lang.annotations.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ActivityViewmodel @Inject constructor(
    private val repository: ActivityRepository,
    private val locationProvider: LocationProvider,
    private val stepCounterManager: StepCounterManager
) : ViewModel(), PermissionManager {

    var state by mutableStateOf(ActivityState())
    var location = locationProvider.liveLocation
    var step = stepCounterManager.liveSteps
    var chronometer: MutableLiveData<Long> = MutableLiveData(0)

    private val timerHandler: Handler = Handler()
    private lateinit var timerRunnable: Runnable
    private var time: Long = 0

    override val isPermissionRequestButtonClicked: MutableState<Boolean> = mutableStateOf(false)

    init {
        timerRunnable = Runnable {
            timerHandler.postDelayed(timerRunnable, 1000)
            time = time.plus(1)
            chronometer.value = time
        }

        state = state.copy(
            isActivityRunning = false
        )
    }

    override fun onPermissionGranted() {
        Log.i("ActivityViewmodel", "Ato")
        //locationProvider.getUserLocation()
    }

    private fun startStopActivity() {
        state = if (!state.isActivityRunning) {
            locationProvider.trackUser()
            stepCounterManager.setupStepCounter()
            timerHandler.post(timerRunnable)

            state.copy(isActivityRunning = true)
        } else {
            locationProvider.stopTracking()
            stepCounterManager.unloadStepCounter()
            timerHandler.removeCallbacks(timerRunnable)

            val pace = step.value?.div(time.div(60f))
            Log.i("ActivityViewmodel", "Pace: ${pace?.roundToInt()} steps/min")
            saveActivity(pace!!.toDouble())
            state.copy(isActivityRunning = false)
        }
    }

    private fun saveActivity(pace: Double) {
        Log.i("ActivityViewmodel", "Saving")
        viewModelScope.launch {
            repository.saveActivity(
                Activity(
                    pace = pace,
                    step = step.value!!,
                    duration = time.toDouble() * 1000,
                    distance = 0f,
                    date = Date(),
                    sessionStart = Date()
                )
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = state.isLoading)
                    }
                    is Resource.Success -> {
                        time = 0
                        chronometer.value = time
                        step.value = 0
                    }
                    is Resource.Error -> {
                        state = state.copy(error = resource.message!!)
                    }
                }

            }
        }
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.OnRequestPermissionsButtonClick -> {
                isPermissionRequestButtonClicked.value = true
            }
            is ActivityEvent.OnStartStopActivityButtonClick -> {
                startStopActivity()
            }
        }
    }
}