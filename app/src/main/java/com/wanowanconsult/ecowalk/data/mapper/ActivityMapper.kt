package com.wanowanconsult.ecowalk.data.mapper

import com.wanowanconsult.ecowalk.data.local.ActivityEntity
import com.wanowanconsult.ecowalk.domain.model.Activity

fun ActivityEntity.toActivity(): Activity {
    return Activity(
        sessionStart = sessionStart,
        distance = distance,
        pace = pace,
        duration = duration,
        date = date,
        step = step
    )
}

fun Activity.toActivityEntity(): ActivityEntity{
    return ActivityEntity(
        sessionStart = sessionStart,
        date = date,
        distance = distance,
        duration = duration,
        pace = pace,
        step = step
    )
}