package com.wanowanconsult.ecowalk.presentation.activity

import com.wanowanconsult.ecowalk.util.Resource

data class ActivityState(
    var isPermissionsGranted: Boolean = false,
    var isLoading: Boolean = false
)