package com.wanowanconsult.ecowalk.presentation.home

data class HomeState (
    val activities: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)