package com.example.fleetech.retrofit.response

data class JMyPayment(
    val DeliveryDate: String,
    val DeliveryTime: String,
    val Destination: String,
    val DispatchDate: String,
    val DispatchTime: String,
    val FuelDetail: List<FuelDetail>,
    val OrderID: String,
    val PmtDetail: List<PmtDetail>,
    val Source: String
)