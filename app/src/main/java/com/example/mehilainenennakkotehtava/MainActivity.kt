package com.example.mehilainenennakkotehtava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mehilainenennakkotehtava.ui.theme.MehilainenEnnakkoTehtavaTheme

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val test = loadJsonWithKotlinSerialization(this, "sampleJson.txt")
        println(test)
        enableEdgeToEdge()
        setContent {
            MehilainenEnnakkoTehtavaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MehilainenEnnakkoTehtavaTheme {
        Greeting("Android")
    }
}

fun loadJsonWithKotlinSerialization(context: Context, fileName: String): JsonObject? {
    return try {
        val jsonStr = context.assets.open(fileName).bufferedReader().use { it.readText() }
        Json.parseToJsonElement(jsonStr).jsonObject
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}