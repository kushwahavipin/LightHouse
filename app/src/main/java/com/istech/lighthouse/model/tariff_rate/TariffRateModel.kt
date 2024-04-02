package com.istech.lighthouse.model.tariff_rate

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class TariffRateModel {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {
        @SerializedName("ebRate")
        @Expose
        var ebRate: String? = null

        @SerializedName("dgRate")
        @Expose
        var dgRate: String? = null

        @SerializedName("maintenanceCharge")
        @Expose
        var maintenanceCharge: String? = null

        @SerializedName("waiverCharges")
        @Expose
        var waiverCharges: String? = null

        @SerializedName("waterCharges")
        @Expose
        var waterCharges: String? = null

        @SerializedName("sewageCharges")
        @Expose
        var sewageCharges: String? = null

        @SerializedName("chequeCharges")
        @Expose
        var chequeCharges: String? = null

        @SerializedName("otherCharges")
        @Expose
        var otherCharges: String? = null
    }
}