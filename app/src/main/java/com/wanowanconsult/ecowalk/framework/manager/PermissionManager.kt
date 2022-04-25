package com.wanowanconsult.ecowalk.framework.manager

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

interface PermissionManager {
    val isPermissionRequestButtonClicked: MutableState<Boolean>
    fun onPermissionGranted()
}

@ExperimentalPermissionsApi
private fun PermissionManager.isOnClickPermissionGranted(
    permissionsState: MultiplePermissionsState,
): Boolean {
    return isPermissionRequestButtonClicked.value
            && permissionsState.allPermissionsGranted
}

@ExperimentalPermissionsApi
private fun PermissionManager.isOnClickPermissionDenied(
    permissionsState: MultiplePermissionsState,
): Boolean {
    return !permissionsState.allPermissionsGranted
            && isPermissionRequestButtonClicked.value
}

@Composable
fun HandleRuntimePermission(
    permissionManager: PermissionManager,
    permissionsState: MultiplePermissionsState,
) {
    /*if (!permissionManager.isPermissionRequestButtonClicked.value) {
        Log.d("HandleRuntimePermission", "Granted")
        permissionManager.onPermissionGranted()
    }*/

    if (!permissionManager.isPermissionRequestButtonClicked.value) return

    if (permissionManager.isOnClickPermissionGranted(
            permissionsState,
        )
    ) {
        LaunchedEffect(
            permissionManager.isPermissionRequestButtonClicked.value,
            permissionsState.allPermissionsGranted
        ) {
            Log.d("HandleRuntimePermission", "Granted")
            permissionManager.onPermissionGranted()
        }
    }

    if (permissionManager.isOnClickPermissionDenied(permissionsState)) {
        LaunchedEffect(
            !permissionsState.allPermissionsGranted,
            permissionManager.isPermissionRequestButtonClicked.value
        ) {
            Log.d("HandleRuntimePermission", "Not granted")
            permissionsState.launchMultiplePermissionRequest()

            if (!permissionsState.allPermissionsGranted) {
                return@LaunchedEffect
            }

            if (permissionsState.allPermissionsGranted) {
                permissionManager.isPermissionRequestButtonClicked.value = false
                permissionManager.onPermissionGranted()
            }
        }
    }

}