package de.deutschebahn.bahnhoflive.util.json

import org.json.JSONException
import org.json.JSONObject

fun JSONObject.string(name: String) =
    optString(name, null)

fun JSONObject.int(name: String, exceptionHandler: (JSONException) -> Unit = {}) =
    try {
        getInt(name)
    } catch (e: JSONException) {
        exceptionHandler(e)
        null
    }

fun JSONObject.string(name: String, fallback: () -> String?) =
    string(name) ?: fallback()