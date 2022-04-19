package com.wanowanconsult.ecowalk.data.repository

import com.wanowanconsult.ecowalk.data.datasource.SensorsDatasource

class SensorsRepository(private val sensorsDatasource: SensorsDatasource) {
    suspend fun fetchSensorData() {
        return sensorsDatasource.fetchSensorData()
    }
}