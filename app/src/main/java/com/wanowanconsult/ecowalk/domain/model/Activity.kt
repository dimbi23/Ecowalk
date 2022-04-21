package com.wanowanconsult.ecowalk.domain.model

import java.util.*

data class Activity(
    val sessionStart: Date,
    val distance: Float,
    val pace: Double,
    val step: Int,
    val duration: Double,
    val date: Date
)