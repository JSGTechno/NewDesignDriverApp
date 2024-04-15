package com.example.fleetech.retrofit.response

data class VerifyUserProfile(
    val DeviceID: String,
    val LoginID: Int,
    val MobileNo: String,
    val Msg: String,
    val Pwd: String,
    val Result: Int,
    val UserType: String
)