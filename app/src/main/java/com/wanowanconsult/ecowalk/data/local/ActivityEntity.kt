package com.wanowanconsult.ecowalk.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "session_start") val sessionStart: Date,
    val distance: Float,
    val pace: Double,
    val step: Int,
    val duration: Double,
    val date: Date
)