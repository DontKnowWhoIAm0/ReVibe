package com.revibe.core.network.util

import org.json.JSONObject

fun parseErrorMessage(errorBody: String?, fallbackCode: Int): String {
    return try {
        JSONObject(errorBody ?: "").getString("error")
    } catch (e: Exception) {
        "Ошибка $fallbackCode"
    }
}