package com.istech.lighthouse.model.profile

data class Data(
    val accountName: String,
    val accountNo: String,
    val mobileNumber: String,
    val email: String,
    val address: String,
    val meterSerialNumber: String,
    val billAmount: Float,
    val rechargeDate: String,
    val dueDate: String
)