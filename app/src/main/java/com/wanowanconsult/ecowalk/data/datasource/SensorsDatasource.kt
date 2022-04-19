package com.wanowanconsult.ecowalk.data.datasource

import androidx.work.*
import com.wanowanconsult.ecowalk.data.worker.FetchSensorDataWorker

private const val FETCH_SENSOR_DATA = "FetchSensorData"
private const val TAG_FETCH_SENSOR_DATA = "FetchSensorDataTag"

class SensorsDatasource(
    private val sensorWorker: WorkManager
) {
    fun fetchSensorData() {
        val fetchSensorDataRequest =
            OneTimeWorkRequestBuilder<FetchSensorDataWorker>().setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
                .addTag(TAG_FETCH_SENSOR_DATA)

        sensorWorker.enqueueUniqueWork(
            FETCH_SENSOR_DATA,
            ExistingWorkPolicy.KEEP,
            fetchSensorDataRequest.build()
        )
    }

    fun cancelFetchingSensorData() {
        sensorWorker.cancelAllWorkByTag(TAG_FETCH_SENSOR_DATA)
    }
}