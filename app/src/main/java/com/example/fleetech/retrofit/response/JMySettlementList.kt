package com.example.fleetech.retrofit.response

data class JMySettlementList(
    val VehicleNo: String,
    val SettlementDate: String,
    val Incentive: Int,
    val SettlementAmount: Int,
    val SettleURL: String,
    val Msg: String,
    val Result: Int,
)