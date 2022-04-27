package com.wanowanconsult.ecowalk.presentation.activity

data class ActivityState(
    var isPermissionsGranted: Boolean = false,
    var isLoading: Boolean = false,
    var isActivityRunning: Boolean = false,
    var error: String = ""
)