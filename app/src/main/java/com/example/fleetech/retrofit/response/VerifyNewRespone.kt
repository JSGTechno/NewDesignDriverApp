package com.example.fleetech.retrofit.response

data class VerifyNewRespone(
    val jToken: String,
    val success: Boolean,
    val jData: List<UpdateData>,
    val jUserProfile: List<UpdateUserProfile>
)