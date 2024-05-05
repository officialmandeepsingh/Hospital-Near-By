package com.msingh.hospitalfinder.data.model.places

data class GooglePlaceResponse(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)