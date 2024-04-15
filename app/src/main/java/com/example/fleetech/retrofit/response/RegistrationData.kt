package com.example.fleetech.retrofit.response

data class RegistrationData(
    val DeviceID: String,
    val LoginID: Int,
    val MobileNo: String,
    val Msg: String,
    val Result: Int,
    val UserType: String
)