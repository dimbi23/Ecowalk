package com.wanowanconsult.ecowalk.ui.navigation

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.presentation.destinations.ActivityScreenDestination
import com.wanowanconsult.ecowalk.presentation.destinations.HomeScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.ic_home , R.string.home_screen),
    Activity(ActivityScreenDestination, R.drawable.ic_activity, R.string.activity_screen),
}