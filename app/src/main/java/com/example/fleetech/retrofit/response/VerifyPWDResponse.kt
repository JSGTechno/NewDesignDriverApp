package com.example.fleetech.retrofit.response

data class VerifyPWDResponse(
    val jData: List<VerifyData>,
    val jUserProfile: List<VerifyUserProfile>,
    val success: Boolean
)