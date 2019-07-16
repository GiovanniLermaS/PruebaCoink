package com.example.pruebacoink.Base

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONArray
import java.io.Serializable
import java.util.*

open class BaseModel : Serializable {

    fun toJsonString(): String = Gson().toJson(this)

    fun objectFromJson(json: String?, type: Class<out BaseModel>): BaseModel? {
        var model: BaseModel? = null
        try {
            model = Gson().fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            Log.e("Gson error in", type.toString())
            e.printStackTrace()
        }

        return model
    }

    fun arrayFromJson(json: String, type: Class<out BaseModel>): ArrayList<out BaseModel?> {
        val jsonArray = JSONArray(json)
        return (0 until jsonArray.length())
            .map { jsonArray.get(it).toString() }
            .mapTo(ArrayList()) { BaseModel().objectFromJson(it, type) }
    }
}