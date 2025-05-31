package com.example.mehilainenennakkotehtava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Replace with reading from assets or network if needed
        val jsonString = assets.open("TestiJson.txt").bufferedReader().use { it.readText() }
        val model = Json { ignoreUnknownKeys = true }.decodeFromString<UIModel>(jsonString)

        setContent {
            MaterialTheme {
                DynamicUI(model, lang = "en")
            }
        }
    }
}