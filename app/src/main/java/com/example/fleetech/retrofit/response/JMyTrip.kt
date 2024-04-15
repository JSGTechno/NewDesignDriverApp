package com.example.fleetech.retrofit.response

data class JMyTrip(
    val DeliveryDate: String,
    val DeliveryTime: String,
    val Destination: String,
    val DispatchDate: String,
    val DispatchTime: String,
    val Msg: String,
    val OrderID: Int,
    val PODSubmit: String,
    val Result: Int,
    val SNO: Int,
    val Source: String
)