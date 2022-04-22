package com.wanowanconsult.ecowalk.presentation.activity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.framework.event.RequestPermissionEvent
import com.wanowanconsult.ecowalk.framework.manager.BasePermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class ActivityViewmodel @Inject constructor(
    private val repository: ActivityRepository,
) : ViewModel(), BasePermissionManager {
    var state by mutableStateOf(ActivityState())
    private var permissions: MutableLiveData<Map<String, @JvmSuppressWildcards Boolean>> =
        MutableLiveData()
    var status: MutableLiveData<Boolean> = MutableLiveData(false)

    override var permissionsName: List<String> = listOf(
        "android.permission.ACTIVITY_RECOGNITION",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
    )

    override fun setPermissionStatus(
        permissions: Map<String, @JvmSuppressWildcards Boolean>,
    ) {
        this.permissions.postValue(permissions)
    }

    override fun requestPermissions() {
        EventBus.getDefault().post(RequestPermissionEvent())
    }

    private fun setStatus(permission: Map<String, @JvmSuppressWildcards Boolean>) {
        Log.d("ActivityViewmodel", "Ato: $permission")
        val res = permission.map {
            it.value
        }.toList().reduce { acc, b -> acc && b }
        Log.d("ActivityViewmodel", "Res: $res")
        status.value = res
        Log.d("ActivityViewmodel", "Status: ${status.value}")
    }

    init {
        Log.d("ActivityViewmodel", "Permissions: ${permissions.value}")
        permissions.observeForever { permission ->
            Log.d("ActivityViewmodel", "Permission: $permission")
            setStatus(permission)
        }
        /*permissions.observeForever { permission ->
            val res = permission.map{
                it.value
            }.toList().reduce { acc, b -> acc && b  }
            Log.d("ActivityViewmodel", "Res: $res")
            state.isPermissionsGranted.value = res
        }*/
    }

    fun getAllPermissionStatus() {
        requestPermissions()
    }

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.OnRequestPermissionsButtonClick -> {
                //requestPermissions()
            }
        }
    }
}