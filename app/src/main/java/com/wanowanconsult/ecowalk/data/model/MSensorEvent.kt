package com.wanowanconsult.ecowalk.data.model

class MSensorEvent {
    var type = SensorType.STEP_DETECTOR
    var value = ""
}

enum class SensorType {
    STEP_DETECTOR,
    STEP_COUNTER,
}