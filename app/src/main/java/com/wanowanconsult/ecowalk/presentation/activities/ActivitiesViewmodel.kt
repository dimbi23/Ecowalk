package com.wanowanconsult.ecowalk.presentation.activities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActivitiesViewmodel @Inject constructor(
    private val repository: ActivityRepository,
) : ViewModel() {
    var state by mutableStateOf(ActivitiesState())

    init {
        getTodayActivities()
    }

    fun onEvent(event: ActivitiesEvent) {
        when (event) {
            is ActivitiesEvent.Refresh -> {
                getTodayActivities()
                state = state.copy(
                    isRefreshing = false
                )
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