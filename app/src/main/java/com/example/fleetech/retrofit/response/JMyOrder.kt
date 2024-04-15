package com.example.fleetech.retrofit.response

data class JMyOrder(
    val Destination: String,
    val LRNO: String,
    val Msg: String,
    val OrderID: Int,
    val PODFlag: String,
    val ReportedFlag: String,
    val Result: Int,
    val Source: String,
    val TripStatus: String,
    val OrderType:String
)