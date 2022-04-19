package com.wanowanconsult.ecowalk.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanowanconsult.ecowalk.data.model.MSensorEvent
import com.wanowanconsult.ecowalk.data.repository.SensorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repository: SensorsRepository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
    private var _step: MutableStateFlow<MSensorEvent> = MutableStateFlow(MSensorEvent())
    val step = _step.asStateFlow()

    init {
        viewModelScope.launch {
            repository.fetchSensorData().collectLatest {
                Log.d("ViewModel", "Sensor value: ${it.value}")
            }
        }


    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {

            }
            is HomeEvent.OnStartActivityButtonClick -> {
                repository.startSensor()
                viewModelScope.launch {
                    _step.value = repository.fetchSensorData().value
                }
            }
        }
    }

    /*private fun fetchSensorData(){
        viewModelScope.launch {
            *//*repository.fetchSensorData().collect {
                state.step = it
                Log.d("ViewModel", it.value)
            }*//*
            Log.d("ViewModel", "Senor: ${repository.fetchSensorData().value.value}")
        }
    }*/
}