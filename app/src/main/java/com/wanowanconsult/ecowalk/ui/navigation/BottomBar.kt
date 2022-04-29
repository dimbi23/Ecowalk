package com.wanowanconsult.ecowalk.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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

    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(ImageVector.vectorResource(destination.icon), contentDescription = stringResource(destination.label))},
                //label = { Text(stringResource(destination.label)) },
            )
        }
    }
}