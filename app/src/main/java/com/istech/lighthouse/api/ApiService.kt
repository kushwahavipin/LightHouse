package com.istech.lighthouse.api

import com.google.gson.JsonObject
import com.istech.lighthouse.model.CommonResponse
import com.istech.lighthouse.model.RazorPayOrderModel
import com.istech.lighthouse.model.comparative.BarChartHome
import com.istech.lighthouse.model.dashboard.DashboardModel
import com.istech.lighthouse.model.login.LoginModel
import com.istech.lighthouse.model.mark_as_read.MarkAsRead
import com.istech.lighthouse.model.month_wise.MonthWiseHome
import com.istech.lighthouse.model.notification.NotificationHome
import com.istech.lighthouse.model.ongoingdata.OnGoingReportValues
import com.istech.lighthouse.model.profile.ProfileModel
import com.istech.lighthouse.model.recharge.RechargeData
import com.istech.lighthouse.model.recharge.RechargeHome
import com.istech.lighthouse.model.recharge.RechargeMeter
import com.istech.lighthouse.model.statics.StaticsModel
import com.istech.lighthouse.model.tariff_rate.TariffRateModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface ApiService {
    @POST("Consumer/Login")
    fun loginApi(@Body query: HashMap<String, Any>): Single<LoginModel?>

    @GET("Consumer/ForgotPassword")
    fun forgotPassword(@QueryMap query: HashMap<String, Any>): Single<CommonResponse?>

    @GET("Consumer/Dashboard")
    fun getDashboardData(@HeaderMap query: HashMap<String, Any>): Single<DashboardModel?>

    @GET("Consumer/GetStatics")
    fun getStaticsData(@HeaderMap query: HashMap<String, Any>): Single<StaticsModel?>

    @GET("Consumer/MyProfile")
    fun getProfileData(@HeaderMap query: HashMap<String, Any>): Single<ProfileModel?>
    
//    @POST("Consumer/RechargeMeter")
//    fun getRechargeMeter(@HeaderMap query: HashMap<String, Any>,@Body jsonObject: JsonObject? ): Single<RechargeMeter?>

    @POST("Consumer/RechargeMeter")
    fun getRechargeMeter(
        @HeaderMap query: HashMap<String, Any>,
        @Body jsonObject: JsonObject?
    ): Call<RechargeMeter?>?

    @GET("Consumer/GetRechargeBasicRecentData")
    fun getRecentRecharge(@HeaderMap query: HashMap<String, Any>): Single<RechargeHome?>

    @GET("MeterBill/CreateRazorPayOrder")
    fun getRechargeOrderID(
        @HeaderMap header: HashMap<String, Any>,
        @QueryMap query: HashMap<String, Any>):
            Call<RazorPayOrderModel?>?
    
    @GET("Consumer/GetRechargeHistory")
    fun getRechargeHistory(@HeaderMap header: HashMap<String, Any>,@QueryMap query: HashMap<String, Any> ): Single<RechargeData?>

    @GET("Consumer/GetComperativeData")
    fun getComparativeData(@HeaderMap header: HashMap<String, Any>,@QueryMap query: HashMap<String, Any> ): Single<BarChartHome?>

    @GET("Consumer/GetMonthWiseData")
    fun getMonthWiseData(@HeaderMap header: HashMap<String, Any>,@QueryMap query: HashMap<String, Any> ): Single<MonthWiseHome?>

    @GET("Consumer/GetDateWiseData")
    fun getDateWiseData(@HeaderMap header: HashMap<String, Any>,
                        @QueryMap query: HashMap<String, Any> ): Single<MonthWiseHome?>

    @GET("Consumer/GetDailyData")
    fun getDailyData(@HeaderMap header: HashMap<String, Any>,
                        @QueryMap query: HashMap<String, Any> ): Single<MonthWiseHome?>

    @GET("Consumer/OnGoingReport")
    fun getOngoingvalues(@HeaderMap query: HashMap<String, Any>): Single<OnGoingReportValues?>

    @GET("Consumer/GetNotification")
    fun getNotificationList(@HeaderMap query: HashMap<String, Any>): Single<NotificationHome?>

    @GET("Consumer/GetTariffRate")
    fun getTariffRate(
        @HeaderMap query: HashMap<String, Any>,
    ): Call<TariffRateModel?>?

    @GET("Consumer/MarkAsRead")
    fun getMarkAsRead(
        @HeaderMap header: HashMap<String, Any>,
        @QueryMap query: HashMap<String, Any> ):
            Call<MarkAsRead?>?

}