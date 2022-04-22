package com.wanowanconsult.ecowalk.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.navigation.navigateTo
import com.wanowanconsult.ecowalk.presentation.appDestination

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination = navController.currentBackStackEntryAsState()
        .value?.appDestination()

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(ImageVector.vectorResource(destination.icon), contentDescription = stringResource(destination.label))},
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}