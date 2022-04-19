package com.wanowanconsult.ecowalk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityEntity(
    @PrimaryKey val id: Int? = null
)