package com.wanowanconsult.ecowalk.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "home", start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewmodel = hiltViewModel()
) {
    /*val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )*/

    val state = viewModel.state
    val sensor = viewModel.sensorLiveData.observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        sensor.value?.let { Text(text = it.value) }
        Button(onClick = {viewModel.onEvent(HomeEvent.OnRequestActivityRecognitionButtonClick)}, content = {

        })
    }
}