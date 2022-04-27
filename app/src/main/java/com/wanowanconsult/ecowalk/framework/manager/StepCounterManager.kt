package com.wanowanconsult.ecowalk.framework.manager

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_FASTEST
import android.os.Handler
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import android.widget.Chronometer
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.MutableLiveData
import com.wanowanconsult.ecowalk.EcowalkApplication


class StepCounterManager : SensorEventListener {
    val liveSteps = MutableLiveData(0)

    private val sensorManager by lazy {
        EcowalkApplication.getApplicationContext().getSystemService(SENSOR_SERVICE) as SensorManager
    }

    private val stepCounterSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    private var initialSteps = -1
    override fun onSensorChanged(event: SensorEvent) {
        event.values.firstOrNull()?.toInt()?.let { newSteps ->
            if (initialSteps == -1) {
                initialSteps = newSteps
            }

            val currentSteps = newSteps - initialSteps
            liveSteps.value = currentSteps
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit

    fun setupStepCounter() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SENSOR_DELAY_FASTEST)
        }
    }

    fun unloadStepCounter() {
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this)
        }
    }
}