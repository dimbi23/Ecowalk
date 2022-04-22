package com.wanowanconsult.ecowalk.framework

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wanowanconsult.ecowalk.framework.event.RequestPermissionEvent
import com.wanowanconsult.ecowalk.presentation.NavGraphs
import com.wanowanconsult.ecowalk.presentation.activity.ActivityViewmodel
import com.wanowanconsult.ecowalk.ui.navigation.BottomBar
import com.wanowanconsult.ecowalk.ui.theme.EcowalkTheme
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: ActivityViewmodel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        viewmodel.setPermissionStatus(permissions)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            EcowalkTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRequestPermissionEventEvent(event: RequestPermissionEvent) {
        permissionRequest.launch(viewmodel.permissionsName.toTypedArray())
    }
}