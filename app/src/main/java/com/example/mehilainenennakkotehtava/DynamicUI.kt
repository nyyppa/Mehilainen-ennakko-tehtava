package com.example.mehilainenennakkotehtava

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import kotlin.collections.get
import kotlin.text.get

@Composable
fun DynamicUI(model: UIModel, lang: String = "en") {
    val view = model.views.firstOrNull() ?: return
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(view.rows.size) { index ->
            RenderRow(view.rows[index], lang)
        }
    }
}

@Composable
fun RenderRow(row: Row, lang: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        row.columns.forEach { column ->
            RenderColumn(column, lang)
        }
    }
}

@Composable
fun RenderColumn(column: Column, lang: String) {
    val component = column.component
    val context = LocalContext.current

    when (component?.type) {
        "card" -> Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            column.rows?.forEach { innerRow -> RenderRow(innerRow, lang) }
        }

        "typography" -> {
            val text = component.i18n?.text?.get(lang).orEmpty()
            val variant = component.variant
            val style = when (variant) {
                "title1" -> MaterialTheme.typography.headlineLarge
                "title6" -> MaterialTheme.typography.titleMedium
                "hbody2" -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodySmall
            }
            Text(text, style = style, modifier = Modifier.padding(4.dp))
        }

        "checkBoxItem" -> {
            val label = component.i18n?.label?.get(lang).orEmpty()
            val subLabel = component.i18n?.subLabel?.get(lang).orEmpty()
            var checked by remember { mutableStateOf(component.checked == true) }
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = { checked = it })
                    Text(label)
                }
                if (subLabel.isNotEmpty()) {
                    Text(subLabel, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        "accordion" -> {
            var expanded by remember { mutableStateOf(false) }
            val summary = component.i18n?.summary?.get(lang).orEmpty()
            TextButton(onClick = { expanded = !expanded }) {
                Text(summary)
            }
            if (expanded) {
                column.rows?.forEach { innerRow -> RenderRow(innerRow, lang) }
            }
        }

        "button" -> {
            val buttonText = component.i18n?.text?.get(lang).orEmpty()
            val url = component.callToAction?.metadata?.get("url") as? Map<*, *>?
            var link = url?.get(lang).toString().replace("\"","")
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = {
                    link.let {
                        val intent = Intent(Intent.ACTION_VIEW, link.toUri())
                        context.startActivity(intent)
                    }
                }) {
                    Text(buttonText)
                }
            }
        }
    }
}