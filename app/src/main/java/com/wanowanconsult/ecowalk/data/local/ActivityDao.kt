package com.wanowanconsult.ecowalk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

data class StepSum(var total: Int)

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activityEntity: ActivityEntity)

    @Query("SELECT * FROM activities")
    suspend fun loadTodayActivities(): List<ActivityEntity>

    @Query("SELECT SUM(step) as total FROM activities")
    suspend fun getTodayTotalStep(): StepSum
}