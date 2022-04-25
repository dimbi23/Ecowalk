package com.wanowanconsult.ecowalk.framework.manager

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

@Composable
fun HandleRuntimePermission(
    permissionManager: PermissionManager,
    permissionsState: MultiplePermissionsState,
) {
    if (!permissionManager.isPermissionRequestButtonClicked.value) return

    if (permissionManager.isOnClickPermissionGranted(
            permissionsState,
        )
    ) {
        LaunchedEffect(
            permissionManager.isPermissionRequestButtonClicked.value,
            permissionsState.allPermissionsGranted
        ) {
            permissionManager.onPermissionGranted()
        }
    }
}