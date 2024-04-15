package com.example.fleetech.retrofit.response

import java.io.Serializable

data class UpdateData(
    val DeviceID: String,
    val DriverName: String,
    val LoginDt: String,
    val LoginTime: String,
    val MobileNo: String,
    val Pwd: String,
    val RegNo: String,
    val VehStatus: String,
    val VehSubStatus: String,
    val Location: String,
    val CheckInDate: String
): Serializable