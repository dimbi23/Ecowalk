package com.wanowanconsult.ecowalk.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.ui.component.MapboxView
import com.wanowanconsult.ecowalk.ui.component.rememberMapboxView

@Composable
@Destination(route = "activity")
fun ActivityScreen(
    navigator: DestinationsNavigator,
    viewModel: ActivityViewmodel = hiltViewModel()
) {
    val mapBoxView = rememberMapboxView()
    Column(modifier = Modifier.fillMaxSize()) {
        MapboxView(mapView = mapBoxView)
    }
}