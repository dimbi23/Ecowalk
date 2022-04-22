package com.wanowanconsult.ecowalk.presentation.activity

sealed class ActivityEvent {
    object OnRequestPermissionsButtonClick: ActivityEvent()
}