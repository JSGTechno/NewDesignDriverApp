package com.example.fleetech.retrofit.response

data class JDocument(
    val DocID: Int,
    val DocName: String,
    val DocNo: String,
    val DocURL: String,
    val ExpiryDate: String,
    val Msg: String,
    val Result: Int
)