package com.wanowanconsult.ecowalk.presentation.activity

import androidx.lifecycle.ViewModel
import com.wanowanconsult.ecowalk.data.repository.ActivityRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewmodel @Inject constructor(
    private val repository: ActivityRepositoryImpl,
) : ViewModel() {

}