package com.wanowanconsult.ecowalk.ui.navigation

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.presentation.destinations.*

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.ic_home , R.string.home_screen),
    Activities(ActivitiesScreenDestination, R.drawable.ic_activity_list, R.string.activities_screen),
    Eco(EcoScreenDestination, R.drawable.ic_eco , R.string.eco_screen),
    //Activity(ActivityScreenDestination, R.drawable.ic_activity, R.string.activity_screen),
    Profile(ProfileScreenDestination, R.drawable.ic_person, R.string.profile_screen),
}