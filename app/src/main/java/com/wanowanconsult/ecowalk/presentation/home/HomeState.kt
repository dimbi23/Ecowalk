package com.wanowanconsult.ecowalk.presentation.home

import com.wanowanconsult.ecowalk.domain.model.Activity

data class HomeState (
    var steps: Int = 0,
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)