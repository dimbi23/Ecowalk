package com.wanowanconsult.ecowalk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wanowanconsult.ecowalk.data.service.SensorService
import com.wanowanconsult.ecowalk.ui.theme.EcowalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcowalkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EcowalkTheme {
        Greeting("Android")
    }
}