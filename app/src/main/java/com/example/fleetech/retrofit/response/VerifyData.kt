package com.example.fleetech.retrofit.response

import java.io.Serializable

data class VerifyData(
    val DeviceID: String,
    val DriverName: String,
    val LoginDt: String,
    val LoginTime: String,
    val MobileNo: String,
    val Pwd: String,
    val RegNo: String
):Serializable