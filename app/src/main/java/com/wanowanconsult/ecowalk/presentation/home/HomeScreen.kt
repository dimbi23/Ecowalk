package com.wanowanconsult.ecowalk.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.domain.model.Activity
import com.wanowanconsult.ecowalk.ui.component.MapboxView
import com.wanowanconsult.ecowalk.ui.component.ProgressView
import com.wanowanconsult.ecowalk.ui.component.rememberMapboxView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Destination(route = "home", start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewmodel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        ProgressView(indicatorValue = state.steps, maxIndicatorValue = 1000)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity, modifier: Modifier) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
    ){
        Column(modifier = Modifier.padding(18.dp)) {
            Text(text = SimpleDateFormat("EEEE dd MMM", Locale.getDefault()).format(activity.date))
            Text(text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(activity.date))
            Text(text = "${activity.step.toString()} ${stringResource(R.string.step)}")
            Text(text = "${activity.pace.roundToInt()} ${stringResource(R.string.step)}/min")
        }
    }
}

@Preview
@Composable
fun PreviewActivityItem(){
    ActivityItem(activity = Activity(
        date = Date(Calendar.getInstance().timeInMillis),
        distance = 10f,
        duration = 10.0,
        pace = 10.2,
        sessionStart = Date(),
        step = 200
    ), modifier = Modifier.padding())
}
