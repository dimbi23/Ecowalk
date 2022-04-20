package com.wanowanconsult.ecowalk.framework

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wanowanconsult.ecowalk.framework.event.RequestPermissionEvent
import com.wanowanconsult.ecowalk.framework.manager.PermissionManager.getPermissionStatus
import com.wanowanconsult.ecowalk.presentation.home.HomeViewmodel
import com.wanowanconsult.ecowalk.presentation.home.NavGraphs
import com.wanowanconsult.ecowalk.ui.theme.EcowalkTheme
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: HomeViewmodel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.forEach{ permission ->
            when (permission.key) {
                Manifest.permission.ACTIVITY_RECOGNITION -> {
                    viewmodel.setActivityRecognitionPermissionStatus(
                        getPermissionStatus(
                            this,
                            permission = permission.key
                        )
                    )
                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    viewmodel.setAccessFineLocationPermissionStatus(
                        getPermissionStatus(
                            this,
                            permission = permission.key
                        )
                    )
                }

                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    viewmodel.setAccessCoarseLocationPermissionStatus(
                        getPermissionStatus(
                            this,
                            permission = permission.key
                        )
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcowalkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRequestStoragePermissionEvent(event: RequestPermissionEvent?) {
        permissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )
    }
}