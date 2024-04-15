package com.example.fleetech.retrofit.response

data class PaymentDetailResponse(
    val jMyPayment: List<JMyPayment>,
    val success: Boolean
)