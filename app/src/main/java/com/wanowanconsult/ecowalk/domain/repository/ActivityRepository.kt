package com.wanowanconsult.ecowalk.domain.repository

import com.wanowanconsult.ecowalk.data.local.StepSum
import com.wanowanconsult.ecowalk.domain.model.Activity
import com.wanowanconsult.ecowalk.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ActivityRepository {
    suspend fun getTodayActivities(): Flow<Resource<List<Activity>>>
    suspend fun getTodayTotalStep(): Flow<Resource<StepSum>>
    suspend fun saveActivity(activity: Activity): Flow<Resource<Unit>>
}