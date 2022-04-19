package com.wanowanconsult.ecowalk.data.model

data class MSensorEvent(
    var type: SensorType = SensorType.STEP_DETECTOR,
    var value: String = "",
)

enum class SensorType {
    STEP_DETECTOR,
    STEP_COUNTER,
}