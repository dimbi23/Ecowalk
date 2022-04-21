package com.wanowanconsult.ecowalk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wanowanconsult.ecowalk.util.Converters

@Database(
    entities = [ActivityEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class EcowalkDatabase: RoomDatabase() {
    abstract val activityDao:ActivityDao
}