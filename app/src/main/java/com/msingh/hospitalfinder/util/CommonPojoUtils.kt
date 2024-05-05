package com.msingh.hospitalfinder.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.msingh.hospitalfinder.data.model.CommonResponse
import java.io.Reader

object CommonPojoUtils {
    inline fun <reified T : Any> getResponse(data: JsonObject?): T {
        return Gson().fromJson(data, T::class.java)
    }

    fun getResponse(charStream: Reader): CommonResponse {
        val type = object : TypeToken<CommonResponse>() {}.type
        return Gson().fromJson(charStream, type)
    }
}