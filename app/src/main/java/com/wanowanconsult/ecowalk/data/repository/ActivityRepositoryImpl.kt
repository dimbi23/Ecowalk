package com.wanowanconsult.ecowalk.data.repository

import com.wanowanconsult.ecowalk.data.local.EcowalkDatabase
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepositoryImpl @Inject constructor(
    private val database: EcowalkDatabase,
): ActivityRepository {
    private val dao = database.activityDao


}