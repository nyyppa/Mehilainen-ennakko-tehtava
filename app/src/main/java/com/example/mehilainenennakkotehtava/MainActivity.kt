package com.example.mehilainenennakkotehtava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.Json
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val uiModelState = produceState<UIModel?>(initialValue = null) {
                    delay(2000) // Simulate loading delay
                    val jsonString = assets.open("sampleJson.txt").bufferedReader().use { it.readText() }
                    val model = Json { ignoreUnknownKeys = true }.decodeFromString<UIModel>(jsonString)
                    value = model
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (uiModelState.value == null) {
                        LoadingScreen()
                    } else {
                        DynamicUI(uiModelState.value!!, lang = "fi")
                    }
                }
            }
        }
    }


}
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}