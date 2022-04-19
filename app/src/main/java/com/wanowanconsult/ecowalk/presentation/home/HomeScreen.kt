package com.wanowanconsult.ecowalk.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    val step = viewModel.step.collectAsState().value

    Log.d("HomeScreen", "Step: $step")

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = step.value
        )
        FloatingActionButton(
            onClick = { viewModel.onEvent(HomeEvent.OnStartActivityButtonClick) },
        ){

        }
    }
}