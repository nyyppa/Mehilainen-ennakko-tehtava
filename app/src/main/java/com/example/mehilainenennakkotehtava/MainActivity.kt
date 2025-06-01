package com.example.mehilainenennakkotehtava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.Json
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var selectedLanguage by remember { mutableStateOf("en") }
                val uiModelState = produceState<UIModel?>(initialValue = null) {
                    delay(2000) // Simulate loading delay
                    val jsonString = assets.open("TestiJson.txt").bufferedReader().use { it.readText() }
                    val model = Json { ignoreUnknownKeys = true }.decodeFromString<UIModel>(jsonString)
                    value = model
                }

                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Language toggle buttons
                            LanguageSelector(
                                selectedLanguage = selectedLanguage,
                                onLanguageSelected = { selectedLanguage = it }
                            )

                            // Content
                            Box(modifier = Modifier.weight(1f)) {
                                if (uiModelState.value == null) {
                                    LoadingScreen()
                                } else {
                                    DynamicUI(uiModelState.value!!, lang = selectedLanguage)
                                }
                            }
                        }
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

@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        listOf("en", "fi", "sv").forEach { lang ->
            Button(
                onClick = { onLanguageSelected(lang) },
                enabled = lang != selectedLanguage,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(lang.uppercase())
            }
        }
    }
}