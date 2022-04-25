package com.wanowanconsult.ecowalk.presentation.activity

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.framework.manager.HandleRuntimePermission
import com.wanowanconsult.ecowalk.ui.component.MapboxView
import com.wanowanconsult.ecowalk.ui.component.rememberMapboxView

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
@Destination(route = "activity")
fun ActivityScreen(
    navigator: DestinationsNavigator,
    viewModel: ActivityViewmodel = hiltViewModel()
) {

    val state = viewModel.state
    val mapBoxView = rememberMapboxView()
    val location by viewModel.locations.observeAsState()
    val step by viewModel.step.observeAsState()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACTIVITY_RECOGNITION,
        )
    )

    HandleRuntimePermission(permissionManager = viewModel, permissionsState = permissionsState)

    val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapBoxView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).zoom(15.5).build())
        mapBoxView.gestures.focalPoint = mapBoxView.getMapboxMap().pixelForCoordinate(it)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (permissionsState.allPermissionsGranted) {
            mapBoxView.location.addOnIndicatorPositionChangedListener(
                onIndicatorPositionChangedListener
            )
            Text(text = "Lat: ${location?.first.toString()}, Long: ${location?.second.toString()}")
            Text(text = "Step: ${step.toString()}")
            Button(onClick = {viewModel.startTracking()}) {
                Text(text = "Start")
            }
            MapboxView(mapView = mapBoxView)
        } else {
            /*Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permission")
            }*/
            Button(onClick = { viewModel.isPermissionRequestButtonClicked.value = true }) {
                Text("Request permission")
            }
        }
    }
}