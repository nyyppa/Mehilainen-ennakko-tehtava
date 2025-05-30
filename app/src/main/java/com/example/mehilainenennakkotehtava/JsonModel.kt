package com.example.mehilainenennakkotehtava

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNames

@Serializable
data class UIModel(
    val version: String,
    val author: String,
    val views: List<View>
)

@Serializable
data class View(
    val location: String,
    val style: Style? = null,
    val i18n: I18nTitle? = null,
    val rows: List<Row>
)

@Serializable
data class Style(
    val type: String? = null,
    val horizontalAlignment: String? = null
)

@Serializable
data class I18nTitle(
    val title: I18nText? = null
)

@Serializable
data class Row(
    val columns: List<Column>
)

@Serializable
data class Column(
    val style: Style? = null,
    val component: Component? = null,
    val rows: List<Row>? = null
)

@Serializable
data class Component(
    val type: String,
    val variant: String? = null,
    val i18n: I18nTextObject? = null,
    val disabled: Boolean? = null,
    val checked: Boolean? = null,
    val labelPlacement: String? = null,
    val endIcon: String? = null,
    val callToAction: CallToAction? = null
)

@Serializable
data class I18nTextObject(
    val text: Map<String, String>? = null,
    val label: Map<String, String>? = null,
    val subLabel: Map<String, String>? = null,
    val summary: Map<String, String>? = null
)

@Serializable
data class CallToAction(
    val action: String,
    val metadata: Map<String, JsonElement>
)

@Serializable
data class I18nText(
    val fi: String,
    val en: String,
    val sv: String
)
