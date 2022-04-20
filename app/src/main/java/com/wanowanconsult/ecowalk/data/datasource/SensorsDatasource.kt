package com.wanowanconsult.ecowalk.data.datasource

import android.app.Service
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import com.wanowanconsult.ecowalk.EcowalkApplication
import com.wanowanconsult.ecowalk.data.model.MSensorEvent
import com.wanowanconsult.ecowalk.data.model.SensorType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsDatasource @Inject constructor() {
    val sensorLiveData = SensorLiveData()

    inner class SensorLiveData : LiveData<MSensorEvent>(MSensorEvent(value = "0")), SensorEventListener {
        private val sensorManager
            get() = EcowalkApplication.getApplicationContext()
                .getSystemService(Service.SENSOR_SERVICE) as SensorManager

        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            if (sensorEvent != null) {
                postValue(
                    MSensorEvent(
                        type = SensorType.STEP_COUNTER,
                        value = sensorEvent.values[0].toString()
                    )
                )
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d(TAG, "Sensor: ${sensor!!.name}, Accuracy: $accuracy")
        }

        override fun onActive() {
            super.onActive()
            sensorManager.let { sm ->
                sm.getDefaultSensor(Sensor.TYPE_PROXIMITY).let {
                    sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
                }
            }
        }

        override fun onInactive() {
            super.onInactive()
            sensorManager.unregisterListener(this)
        }
    }

    companion object{
        private const val TAG: String = "SensorsDatasource"
    }
}