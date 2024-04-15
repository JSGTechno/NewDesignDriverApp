package com.example.fleetech.retrofit.response

data class OTPData(
    val DeviceID: String,
    val LoginID: Int,
    val MobileNo: String,
    val Msg: String,
    val Result: Int
)