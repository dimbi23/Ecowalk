package com.wanowanconsult.ecowalk.presentation.activity

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
    val state = viewModel.state
    val status = viewModel.status.observeAsState()
    val mapBoxView = rememberMapboxView()

    LaunchedEffect(true) {
        viewModel.getAllPermissionStatus()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (status.value == true) {
            MapboxView(mapView = mapBoxView)
        }
        Text(text = status.value.toString())
        MapboxView(mapView = mapBoxView)
    }
}