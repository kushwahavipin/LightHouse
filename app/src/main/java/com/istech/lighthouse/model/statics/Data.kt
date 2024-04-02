package com.istech.lighthouse.model.statics

data class Data(
    val balance: Float,
    val currentMonthConsumption: Float,
    val consumerNo: String,
    val consumerName: String,
    val meterSerialNumber: String,
    val dgReading: String,
    val gridReading: String,
    val gridStarted: String,
    val rechargeOn: String,
    val todayConsumption: Float,
    val unitNumber: Float,
    val updatedOn: String
)