package com.example.fleetech.retrofit.response

data class JMyOrderX(
    val Destination: String,
    val LRNO: String,
    val LoadingSlipFlag: String,
    val Msg: String,
    val OrderID: Int,
    val Result: Int,
    val Source: String,
    val TripStatus: String,
    val OrderType:String
)