package com.wanowanconsult.ecowalk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wanowanconsult.ecowalk.data.service.SensorService
import com.wanowanconsult.ecowalk.presentation.home.NavGraphs
import com.wanowanconsult.ecowalk.ui.theme.EcowalkTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

    override fun onStart() {
        super.onStart()
        Intent(this, SensorService::class.java).also { intent ->
            startService(intent)
        }
    }

    override fun onStop() {
        super.onStop()

        Intent(this, SensorService::class.java).also { intent ->
            stopService(intent)
        }
    }
}