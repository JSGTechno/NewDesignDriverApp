package com.example.fleetech.retrofit.response

data class TripDetailResponse(
    val jTripData: List<JTripData>,
    val jWalletBalance: String,
    val success: Boolean
)