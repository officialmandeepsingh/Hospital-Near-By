package com.msingh.hospitalfinder.data.model.distance

data class LocationDistance(
    val destination_addresses: List<String>,
    val origin_addresses: List<String>,
    val rows: List<Row>,
    val status: String
)