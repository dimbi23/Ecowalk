package com.wanowanconsult.ecowalk.presentation.activities

import com.wanowanconsult.ecowalk.domain.model.Activity

data class ActivitiesState (
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)