package com.istech.lighthouse.model.ongoingdata

data class OnGoingReportData (
    val registersID : Int,
    val updatedDate : String,
    val voltage1 : String,
    val voltage2 : String,
    val voltage3 : String,
    val watts1 : String,
    val watts2 : String,
    val watts3 : String,
    val activePower : String,
    val apparentPower : String,
    val powerFactor : String,
    val kwhGrid : String,
    val kvahGrid : String,
    val kwhDG : String,
    val kvahDG : String,
        )