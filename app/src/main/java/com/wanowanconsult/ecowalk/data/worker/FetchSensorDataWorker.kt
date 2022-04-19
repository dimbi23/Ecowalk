package com.wanowanconsult.ecowalk.data.worker

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.wanowanconsult.ecowalk.EcowalkApplication
import com.wanowanconsult.ecowalk.R

class FetchSensorDataWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), SensorEventListener {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    private val sensorManager
        get() = EcowalkApplication.getApplicationContext()
            .getSystemService(Service.SENSOR_SERVICE) as SensorManager

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val progress = applicationContext.getString(R.string.start_ecowalking)

        return ForegroundInfo(
            NOTIFICATION_ID, createNotification(progress = progress)
        )
    }

    override suspend fun doWork(): Result = try {
        setForeground(getForegroundInfo())
        fetchSensorData()
        Result.success()
    } catch (error: Throwable) {
        Result.failure()
    }

    private fun fetchSensorData() {
        sensorManager.let { sm ->
            sm.getDefaultSensor(Sensor.TYPE_PROXIMITY).let {
                sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }

    private fun createNotification(progress: String): Notification {
        val id = applicationContext.getString(R.string.notification_channel_id)
        val title = applicationContext.getString(R.string.notification_title)
        val cancel = applicationContext.getString(R.string.cancel_walking)

        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        return NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        // Create a Notification channel
    }

    companion object {
        private const val NOTIFICATION_ID = 10
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}