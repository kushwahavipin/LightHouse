package com.istech.lighthouse.model.month_wise

data class MonthWiseHome(
    val `data`: ArrayList<MonthWiseData>,
    val message: String,
    val success: Boolean
)
