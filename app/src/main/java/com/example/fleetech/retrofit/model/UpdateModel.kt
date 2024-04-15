package com.example.fleetech.retrofit.model

data class UpdateModel(
    val MobileNo: String,
    val pwd: String,
    val UserType: String,
    val Latitude: Double?,
    val Longitude: Double?,
    var Location: String?
)