package com.wanowanconsult.ecowalk.framework

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wanowanconsult.ecowalk.framework.event.StartActivityEvent
import com.wanowanconsult.ecowalk.framework.service.ChronometerService
import com.wanowanconsult.ecowalk.presentation.NavGraphs
import com.wanowanconsult.ecowalk.presentation.activity.ActivityViewmodel
import com.wanowanconsult.ecowalk.ui.navigation.BottomBar
import com.wanowanconsult.ecowalk.ui.theme.EcowalkTheme
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference


private const val MSG_UPDATE_TIME = 0

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private var serviceBound = false
    private var chronometerService: ChronometerService? = null
    private val viewModel by viewModels<ActivityViewmodel>()
    private val mUpdateTimeHandler: Handler = UIUpdateHandler(this)

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            EcowalkTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(navController)
                    }
                )
                {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service")
        }

        val i = Intent(this, ChronometerService::class.java)
        startService(i)
        bindService(i, mConnection, 0)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onStartActivityEvent(event: StartActivityEvent) {
        if (serviceBound && !chronometerService!!.isChronometerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Starting timer")
            }
            chronometerService!!.startChronometer()
            updateUIStartRun()
        } else if (serviceBound && chronometerService!!.isChronometerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Stopping timer")
            }
            chronometerService!!.stopChronometer()
            updateUIStopRun()
        }
    }

    /**
     * Updates the UI when a run starts
     */
    private fun updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
    }

    /**
     * Updates the UI when a run stops
     */
    private fun updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        if (serviceBound) {
            if (chronometerService!!.isChronometerRunning()) {
                chronometerService!!.foreground()
            } else {
                stopService(Intent(this, ChronometerService::class.java))
            }
            unbindService(mConnection)
            serviceBound = false
        }
        super.onStop()
    }


    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound")
            }
            val binder: ChronometerService.RunServiceBinder = service as ChronometerService.RunServiceBinder
            chronometerService = binder.service
            serviceBound = true

            chronometerService!!.background()

            if (chronometerService!!.isChronometerRunning()) {
                updateUIStartRun()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect")
            }
            serviceBound = false
        }
    }

    private fun updateUIChronometer() {
        if (serviceBound) {
            viewModel.liveChronometer.value = chronometerService!!.getTime()
        }
    }

    internal class UIUpdateHandler(activity: MainActivity) : Handler() {
        private val activity: WeakReference<MainActivity>

        override fun handleMessage(message: Message) {
            if (MSG_UPDATE_TIME == message.what) {
                activity.get()?.updateUIChronometer()
                sendEmptyMessageDelayed(MSG_UPDATE_TIME, UPDATE_RATE_MS.toLong())
            }
        }

        companion object {
            private const val UPDATE_RATE_MS = 1000
        }

        init {
            this.activity = WeakReference<MainActivity>(activity)
        }
    }
}