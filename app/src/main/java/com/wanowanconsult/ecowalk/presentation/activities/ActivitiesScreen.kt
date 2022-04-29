package com.wanowanconsult.ecowalk.presentation.activities

import android.annotation.SuppressLint
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.presentation.activity.ActivityEvent
import com.wanowanconsult.ecowalk.presentation.home.ActivityItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
@Destination(route = "activities")
fun ActivitiesScreen(
    navigator: DestinationsNavigator,
    viewModel: ActivitiesViewmodel = hiltViewModel()
) {
    val state = viewModel.state
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.title_activity_list)) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(painterResource(R.drawable.ic_activity), contentDescription = "Localized description")
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.activities.size) { i ->
                val activity = state.activities[i]
                ActivityItem(
                    activity = activity,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable { navigator.navigateUp() }
                )
            }
        }
    }
}