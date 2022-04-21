package com.wanowanconsult.ecowalk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ActivityEntity(
    @PrimaryKey val id: Int? = null,
    val sessionStart: Calendar,
    val distance: Float,
    val pace: Double,
    val duration: Double,
    val date: Calendar
)