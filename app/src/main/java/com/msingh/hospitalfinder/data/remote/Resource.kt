package com.msingh.hospitalfinder.data.remote

import com.msingh.hospitalfinder.data.model.CommonResponse
import com.msingh.hospitalfinder.util.CommonPojoUtils
import com.squareup.okhttp.ResponseBody


enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    GATEWAY
}

class Resource <out T>(val status: Status, val data: T?,val message: String?){
    companion object{
        fun <T> success(data: T?, message: String? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> error(message: ResponseBody, data: T?): Resource<T> {
            val errorResponse: CommonResponse = CommonPojoUtils.getResponse(message.charStream())
            return Resource(Status.ERROR, data, errorResponse.message)
        }

        fun <T> timeOut(message: String): Resource<T> {
            return Resource(status = Status.GATEWAY, message = message, data = null)
        }
    }
}