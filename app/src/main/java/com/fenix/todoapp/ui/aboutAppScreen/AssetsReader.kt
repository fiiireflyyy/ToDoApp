package com.fenix.todoapp.ui.aboutAppScreen

import android.content.Context
import org.json.JSONObject

class AssetsReader(private val context: Context) {

    fun read(filename : String): JSONObject {
        val data = IOUtils.toString(context.assets.open(filename))
        return JSONObject(data)
    }
}