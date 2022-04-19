package com.wanowanconsult.ecowalk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ActivityEntity::class],
    version = 1
)
abstract class EcowalkDatabase: RoomDatabase() {
    abstract val activityDao:ActivityDao
}