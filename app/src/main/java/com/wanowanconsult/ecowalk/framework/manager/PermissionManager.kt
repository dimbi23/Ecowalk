package com.wanowanconsult.ecowalk.framework.manager

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


enum class PermissionStatus {
    CAN_ASK_PERMISSION, PERMISSION_GRANTED, PERMISSION_DENIED
}

object PermissionManager {
    fun getPermissionStatus(
        activity: Activity,
        permission: String
    ): PermissionStatus {
        return if (PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(activity, permission)
        ) {
            PermissionStatus.PERMISSION_GRANTED
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            )
        ) {
            PermissionStatus.CAN_ASK_PERMISSION
        } else {
            PermissionStatus.PERMISSION_DENIED
        }
    }
}