package com.wanowanconsult.ecowalk.data.repository

import com.wanowanconsult.ecowalk.data.local.EcowalkDatabase
import com.wanowanconsult.ecowalk.data.local.StepSum
import com.wanowanconsult.ecowalk.data.mapper.toActivity
import com.wanowanconsult.ecowalk.domain.model.Activity
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepositoryImpl @Inject constructor(
    database: EcowalkDatabase,
): ActivityRepository {
    private val dao = database.activityDao

    override suspend fun getTodayActivities(today: Date): Flow<Resource<List<Activity>>> {
        return flow {
            emit(Resource.Loading(true))
            val activities = dao.loadTodayActivities(today)
            emit(Resource.Success(
                data = activities.map { it.toActivity() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTodayTotalStep(today: Date): Flow<Resource<StepSum>> {
        return flow {
            emit(Resource.Loading(true))
            val stepSum = dao.getTodayTotalStep(today)
            emit(Resource.Success(
                data = stepSum
            ))
            emit(Resource.Loading(false))
        }
    }
}