package com.example.fleetech.retrofit.response

data class UpdatePWDResponse(
    val jData: List<UpdateData>,
    val jUserProfile: List<UpdateUserProfile>,
    val success: Boolean,
    val jToken: String
)