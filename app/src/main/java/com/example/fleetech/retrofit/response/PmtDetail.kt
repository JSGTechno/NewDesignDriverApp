package com.example.fleetech.retrofit.response

data class PmtDetail(
    val Amount: String,
    val OrderID: String,
    val PmtDate: String,
    val PmtType: String,
    val UTRNo: String
)