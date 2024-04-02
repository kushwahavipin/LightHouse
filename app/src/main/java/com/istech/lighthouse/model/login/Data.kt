package com.istech.lighthouse.model.login

data class Data(
    val fullName: String,
    val roleType: String,
    val token: String,
    val userID: Int,
    val userName: String,
    val billingType:String,
    val razorpayKey:String
)