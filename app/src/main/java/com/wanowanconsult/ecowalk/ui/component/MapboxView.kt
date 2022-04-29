package com.wanowanconsult.ecowalk.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.location
import com.wanowanconsult.ecowalk.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun rememberMapboxView(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    return mapView
}

@Composable
fun MapboxView(mapView: MapView, modifier: Modifier) {
    AndroidView(modifier = modifier, factory = { mapView }) {
        CoroutineScope(Dispatchers.Main).launch {
            val map = mapView.getMapboxMap()
            map.loadStyleUri("mapbox://styles/dimbi23/cl2eheoox000d15o2eji2cdci") {
                mapView.location.updateSettings {
                    enabled = true
                    pulsingEnabled = true
                }
            }
        }
    }
}