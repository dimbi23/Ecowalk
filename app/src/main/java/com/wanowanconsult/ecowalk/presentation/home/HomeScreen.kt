package com.wanowanconsult.ecowalk.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.domain.model.Activity
import com.wanowanconsult.ecowalk.ui.component.MapboxView
import com.wanowanconsult.ecowalk.ui.component.ProgressView
import com.wanowanconsult.ecowalk.ui.component.rememberMapboxView

@Destination(route = "home", start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewmodel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        ProgressView(indicatorValue = state.steps)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.activities.size) { i ->
                val activity = state.activities[i]
                ActivityItem(
                    activity = activity,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigateUp()
                        }
                        .padding(16.dp)
                )
                if (i < state.activities.size) {
                    Divider(
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity, modifier: Modifier) {
    Text(text = activity.step.toString())
}
