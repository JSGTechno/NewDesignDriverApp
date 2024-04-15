package com.example.fleetech.retrofit.response

data class DispatchOrderListResponse(
    val jMyOrderList: List<JMyOrder>,
    val success: Boolean
)