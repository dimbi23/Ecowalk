package com.wanowanconsult.ecowalk.framework.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.wanowanconsult.ecowalk.R
import com.wanowanconsult.ecowalk.framework.MainActivity

private const val NOTIFICATION_ID = 1
private val TAG: String = ChronometerService::class.java.simpleName

class ChronometerService: Service() {
    private var isChronometerRunning = false
    private val serviceBinder: IBinder = RunServiceBinder()
    private val timerHandler: Handler = Handler()
    private lateinit var timerRunnable: Runnable
    private var time: Long = 0

    fun getTime(): Long {
        return time
    }

    inner class RunServiceBinder : Binder() {
        val service: ChronometerService
            get() = this@ChronometerService
    }

    override fun onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service")
        }

        timerRunnable = Runnable {
            timerHandler.postDelayed(timerRunnable, 1000)
            time = time.plus(1)
        }

        isChronometerRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service")
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Binding service")
        }
        return serviceBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Destroying service")
        }
    }

    fun startChronometer() {
        if (!isChronometerRunning) {
            timerHandler.post(timerRunnable)
        } else {
            Log.e(TAG, "startTimer request for an already running timer")
        }
    }

    fun stopChronometer() {
        if (isChronometerRunning) {
            timerHandler.removeCallbacks(timerRunnable)
        } else {
            Log.e(TAG, "stopTimer request for a timer that isn't running")
        }
    }

    fun isChronometerRunning(): Boolean {
        return isChronometerRunning
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun foreground() {
        startForeground(NOTIFICATION_ID, createNotification())
    }

    fun background() {
        stopForeground(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createNotification(): Notification {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setContentTitle("Timer Active")
            .setContentText("Tap to return to the timer")
            .setSmallIcon(R.drawable.ic_activity)

        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent,
            FLAG_IMMUTABLE
        )
        builder.setContentIntent(resultPendingIntent)
        return builder.build()
    }
}