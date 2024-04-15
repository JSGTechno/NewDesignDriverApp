package com.example.fleetech.retrofit.response

data class ResendOtpResponse(
    val jData: List<ResendOTPData>,
    val success: Boolean
)