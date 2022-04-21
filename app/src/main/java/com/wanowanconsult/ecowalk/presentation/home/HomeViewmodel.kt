package com.wanowanconsult.ecowalk.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wanowanconsult.ecowalk.data.repository.ActivityRepositoryImpl
import com.wanowanconsult.ecowalk.framework.manager.PermissionStatus
import com.wanowanconsult.ecowalk.framework.event.RequestPermissionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(
    repository: ActivityRepositoryImpl,
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    private var activityRecognitionPermissionStatus: PermissionStatus? = null
    private var accessCoarseLocationPermissionStatus: PermissionStatus? = null
    private var accessFineLocationPermissionStatus: PermissionStatus? = null

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {

            }
            is HomeEvent.OnRequestActivityRecognitionButtonClick -> {
                requestPermission()
            }
        }
    }

    fun setActivityRecognitionPermissionStatus(newPermissionStatus: PermissionStatus){
        if (activityRecognitionPermissionStatus !== newPermissionStatus) {
            activityRecognitionPermissionStatus = newPermissionStatus
            if (activityRecognitionPermissionStatus === PermissionStatus.PERMISSION_GRANTED) {
                Log.d("HomeViewmodel", "activityRecognitionPermissionStatus granted")
            }
        }
    }

    fun setAccessCoarseLocationPermissionStatus(newPermissionStatus: PermissionStatus){
        if (accessCoarseLocationPermissionStatus !== newPermissionStatus) {
            accessCoarseLocationPermissionStatus = newPermissionStatus
            if (accessCoarseLocationPermissionStatus === PermissionStatus.PERMISSION_GRANTED) {
                Log.d("HomeViewmodel", "accessCoarseLocationPermissionStatus granted")
            }
        }
    }

    fun setAccessFineLocationPermissionStatus(newPermissionStatus: PermissionStatus){
        if (accessFineLocationPermissionStatus !== newPermissionStatus) {
            accessFineLocationPermissionStatus = newPermissionStatus
            if (accessFineLocationPermissionStatus === PermissionStatus.PERMISSION_GRANTED) {
                Log.d("HomeViewmodel", "accessFineLocationPermissionStatus granted")
            }
        }
    }

    private fun requestPermission(){
        EventBus.getDefault().post(RequestPermissionEvent())
    }
}