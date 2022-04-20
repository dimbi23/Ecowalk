package com.wanowanconsult.ecowalk.data.repository

import com.wanowanconsult.ecowalk.data.datasource.SensorsDatasource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsRepository @Inject constructor(private val sensorsDatasource: SensorsDatasource) {
    fun fetchSensorData(): SensorsDatasource.SensorLiveData {
        return sensorsDatasource.sensorLiveData
    }
}