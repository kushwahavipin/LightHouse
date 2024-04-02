package com.istech.lighthouse.model.recharge

data class RecentPaymentModel(
    var consumerID: String,
    var transactionID: String,
    var rechargeDate: String,
    var amount: String,
    var status: String
)
