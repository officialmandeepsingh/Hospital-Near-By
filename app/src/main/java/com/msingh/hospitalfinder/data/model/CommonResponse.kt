package com.msingh.hospitalfinder.data.model

import com.google.gson.JsonObject

data class CommonResponse(
    val data: JsonObject?,
    val message: String,
    val statusCode: Int
)