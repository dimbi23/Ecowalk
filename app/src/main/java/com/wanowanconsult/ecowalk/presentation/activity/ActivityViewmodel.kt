package com.wanowanconsult.ecowalk.presentation.activity

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.framework.event.StartActivityEvent
import com.wanowanconsult.ecowalk.framework.event.StartServiceEvent
import com.wanowanconsult.ecowalk.framework.event.StopActivityEvent
import com.wanowanconsult.ecowalk.framework.manager.LocationProvider
import com.wanowanconsult.ecowalk.framework.manager.PermissionManager
import com.wanowanconsult.ecowalk.framework.manager.StepCounterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewmodel @Inject constructor(
    private val repository: ActivityRepository,
    private val locationProvider: LocationProvider,
    private val stepCounterManager: StepCounterManager
) : ViewModel(), PermissionManager {

    var state by mutableStateOf(ActivityState())
    var location = locationProvider.liveLocation
    var step = stepCounterManager.liveSteps
    var chrono = stepCounterManager.liveChronometer
    var liveChronometer = MutableLiveData<Long>(0)

    override val isPermissionRequestButtonClicked: MutableState<Boolean> = mutableStateOf(false)

    init {
        Log.d("ActivityViewmodel", "Ato")
        stepCounterManager.setupChronometer()
    }

    override fun onPermissionGranted() {
        Log.d("ActivityViewmodel", "Ato")
        //locationProvider.getUserLocation()
    }

    fun formatChrono(time: Long?): String {
        var result = ""
        time?.let {
            result = SimpleDateFormat("mm:ss", Locale.FRANCE).format(Date(it*1000))
        }

        return result
    }

    fun startChronometer(){
        EventBus.getDefault().post(StartActivityEvent())
    }

    fun startTracking(){
        locationProvider.trackUser()
        stepCounterManager.setupStepCounter()
        stepCounterManager.startChronometer()
    }

    fun stopTracking(){
        locationProvider.stopTracking()
        stepCounterManager.unloadStepCounter()
        stepCounterManager.stopChronometer()

        /*viewModelScope.launch {
            step.value?.let { _step ->
                repository.saveActivity(Activity(
                    step = _step,
                     pace =
                ))
            }
        }*/
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.OnRequestPermissionsButtonClick -> {
                isPermissionRequestButtonClicked.value = true
            }
        }
    }
}