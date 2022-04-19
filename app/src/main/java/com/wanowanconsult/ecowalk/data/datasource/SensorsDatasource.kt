package com.wanowanconsult.ecowalk.data.datasource

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.work.*
import com.wanowanconsult.ecowalk.EcowalkApplication
import com.wanowanconsult.ecowalk.data.model.MSensorEvent
import com.wanowanconsult.ecowalk.data.service.SensorService
import com.wanowanconsult.ecowalk.data.worker.FetchSensorDataWorker
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsDatasource @Inject constructor(private var sensorService: SensorService ) {
    private var bound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as SensorService.LocalBinder
            sensorService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    fun fetchSensorData(): StateFlow<MSensorEvent> {
        return sensorService.msgEvent
    }

    fun startSensor(){
        Intent(EcowalkApplication.getApplicationContext(), SensorService::class.java).also { intent ->
            EcowalkApplication.getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun stopSensor(){
        EcowalkApplication.getApplicationContext().unbindService(connection)
    }
}