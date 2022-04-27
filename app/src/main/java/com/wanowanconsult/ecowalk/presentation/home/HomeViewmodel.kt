package com.wanowanconsult.ecowalk.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repository: ActivityRepository,
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    init {
        getTodayActivities()
        getTodayTotalStep()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {

            }
        }
    }

    private fun getTodayTotalStep() {
        viewModelScope.launch {
            repository.getTodayTotalStep()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { stepSum ->
                                state = state.copy(
                                    steps = stepSum.total
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun getTodayActivities() {
        viewModelScope.launch {
            repository.getTodayActivities()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { activities ->
                                state = state.copy(
                                    activities = activities
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}