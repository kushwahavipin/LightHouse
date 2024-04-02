package com.istech.lighthouse.model.dashboard

data class Data(
    val balance: Float,
    val notificationCount: Int,
    val lastUpdatedDate: String,
    val consumerName: String,
    val isDeleted:Boolean
)