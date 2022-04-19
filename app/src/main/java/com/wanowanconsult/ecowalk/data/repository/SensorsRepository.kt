package com.wanowanconsult.ecowalk.data.repository

import com.wanowanconsult.ecowalk.data.datasource.SensorsDatasource
import com.wanowanconsult.ecowalk.data.model.MSensorEvent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsRepository @Inject constructor(private val sensorsDatasource: SensorsDatasource) {
    fun fetchSensorData(): StateFlow<MSensorEvent> {
        return sensorsDatasource.fetchSensorData()
    }

    fun startSensor(){
        sensorsDatasource.startSensor()
    }

    fun stopSensor(){
        sensorsDatasource.stopSensor()
    }
}