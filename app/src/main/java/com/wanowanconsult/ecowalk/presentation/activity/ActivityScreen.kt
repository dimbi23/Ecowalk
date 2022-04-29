package com.wanowanconsult.ecowalk.presentation.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.framework.manager.HandleRuntimePermission
import com.wanowanconsult.ecowalk.ui.component.ChronometerView
import com.wanowanconsult.ecowalk.ui.component.MapboxView
import com.wanowanconsult.ecowalk.ui.component.rememberMapboxView
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanowanconsult.ecowalk.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
@Destination(route = "activity")
fun ActivityScreen(
    navigator: DestinationsNavigator,
    viewModel: ActivityViewmodel = hiltViewModel()
) {
    val state = viewModel.state
    val mapBoxView = rememberMapboxView()
    val location by viewModel.location.observeAsState()
    val step by viewModel.step.observeAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapBoxView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).zoom(15.5).build())
        mapBoxView.gestures.focalPoint = mapBoxView.getMapboxMap().pixelForCoordinate(it)
    }

    mapBoxView.location.addOnIndicatorPositionChangedListener(
        onIndicatorPositionChangedListener
    )

    LaunchedEffect(false) {
        if (permissionsState.allPermissionsGranted) {
            viewModel.onPermissionGranted()
        }
    }

    HandleRuntimePermission(permissionManager = viewModel, permissionsState = permissionsState)

    if (permissionsState.allPermissionsGranted) {

        //if(state.isLoading) CircularProgressIndicator()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Box(
                    Modifier.fillMaxWidth().height(128.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Swipe up to expand sheet")
                }
                Column(
                    Modifier.fillMaxWidth().padding(64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sheet content")
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            scope.launch { scaffoldState.bottomSheetState.collapse() }
                        }
                    ) {
                        Text("Click to collapse sheet")
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {viewModel.onEvent(ActivityEvent.OnStartStopActivityButtonClick)}
                ) {
                    Icon(painterResource(R.drawable.ic_activity), contentDescription = "Localized description")
                }
            },
        ) { innerPadding ->
            MapboxView(mapView = mapBoxView, modifier = Modifier.padding(innerPadding))
        }


        /*Column(modifier = Modifier.fillMaxSize()) {
            mapBoxView.location.addOnIndicatorPositionChangedListener(
                onIndicatorPositionChangedListener
            )
            Text(text = "Lat: ${location?.first.toString()}, Long: ${location?.second.toString()}")
            Text(text = "Step: ${step.toString()}")

            ChronometerView(viewModel)
            Text(text = state.error)
            Button(onClick = { viewModel.onEvent(ActivityEvent.OnStartStopActivityButtonClick) }) {
                Text(text = if (state.isActivityRunning) "Stop" else "Start")
            }

            MapboxView(mapView = mapBoxView)
        }*/
    } else {
        Button(onClick = { viewModel.isPermissionRequestButtonClicked.value = true }) {
            Text("Request permission")
        }
    }
}