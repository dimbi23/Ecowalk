package com.wanowanconsult.ecowalk.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wanowanconsult.ecowalk.data.repository.SensorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repository: SensorsRepository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
    var sensorLiveData = repository.fetchSensorData()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {

            }
        }
    }
}