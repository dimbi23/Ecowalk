package com.wanowanconsult.ecowalk.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.wanowanconsult.ecowalk.presentation.activity.ActivityViewmodel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChronometerView(viewModel: ActivityViewmodel) {
    val chronometer by viewModel.chronometer.observeAsState()

    chronometer?.let {
        Text(text = SimpleDateFormat("mm:ss", Locale.FRANCE).format(Date(it * 1000)))
    }
}