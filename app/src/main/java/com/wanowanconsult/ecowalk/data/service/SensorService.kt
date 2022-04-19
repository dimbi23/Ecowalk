package com.wanowanconsult.ecowalk.data.service

import android.app.*
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.wanowanconsult.ecowalk.MainActivity
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.data.model.MSensorEvent
import com.wanowanconsult.ecowalk.data.model.SensorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorService @Inject constructor() : Service(), SensorEventListener {
    private val sensorManager
        get() = getSystemService(SENSOR_SERVICE) as SensorManager
    private val binder = LocalBinder()

    private val _msgEvent = MutableStateFlow(MSensorEvent())
    val msgEvent: StateFlow<MSensorEvent> = _msgEvent

    inner class LocalBinder : Binder() {
        fun getService(): SensorService = this@SensorService
    }

    override fun onCreate() {
        initSensor()
        createNotificationChannel()
    }

    private fun initSensor() {
        sensorManager.let { sm ->
            sm.getDefaultSensor(Sensor.TYPE_PROXIMITY).let {
                sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setContentIntent(pendingIntent)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "My service channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.values.isNotEmpty()) {
            _msgEvent.value = MSensorEvent(
                type = SensorType.STEP_COUNTER,
                value = sensorEvent.values[0].toString()
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "Accuracy: $accuracy")
    }

    companion object {
        private const val TAG: String = "ServiceStartArguments"
        private const val ONGOING_NOTIFICATION_ID: Int = 10
        private const val CHANNEL_ID: String = "Sensor Channel"
    }
}