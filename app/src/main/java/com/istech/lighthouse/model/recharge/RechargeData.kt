package com.istech.lighthouse.model.recharge

data class RechargeData (
    val `data`:  ArrayList<RecentPaymentModel>,
    val message: String,
    val success: Boolean,
    val isChequeBounce: Boolean,
    val lastUpdated : String,
    val currentAmount : String,
    val lastChequeAmount : Double,
    val penalityAmount : Double,
    val consumerID : String,
    val mobileNo:String,
    val email:String,
    val getRecents : ArrayList<RecentPaymentModel>,
        )