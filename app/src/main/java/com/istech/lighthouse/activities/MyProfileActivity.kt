package com.istech.lighthouse.activities

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.istech.lighthouse.R
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.databinding.ActivityMyProfileBinding
import com.istech.lighthouse.databinding.LayoutCurrentRatesBinding
import com.istech.lighthouse.model.tariff_rate.TariffRateModel
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.viewmodel.profile.ProfileVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MyProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var ratesBinding: LayoutCurrentRatesBinding
    private lateinit var activity: MyProfileActivity
    private lateinit var context: Context
    private lateinit var profileVM: ProfileVM
    private lateinit var chargeDialog: Dialog
    private val TAG = "MyProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        activity = this
        context = activity
        handleClick()
        initView()
        getData()
        iniObserver()
    }

    private fun handleClick() {
//        binding.rlHeader.apply {
            binding.ivBack.setOnClickListener {
                onBackPressed()
            }
            binding.logout.setOnClickListener {

                showLogoutDialog()
            }
            binding.cardCurrentApplicable.setOnClickListener {
                openDialog()
            }
//            tvTitle.text = "My Profile"
//        }
    }

    private fun showLogoutDialog() {
        val ad = AlertDialog.Builder(this)
        ad.setTitle("Sure Logout")
        ad.setMessage("Do You Want To Logout ?")
        ad.setNegativeButton("No") { dialog: DialogInterface, i: Int -> dialog.dismiss() }
        ad.setPositiveButton(
            "Yes"
        ) { dialog: DialogInterface?, i: Int -> logout(dialog) }
        ad.create()
        ad.show()
    }

    private fun logout(dialog: DialogInterface?) {
        sessionManager.clear()
        val intent = Intent(this@MyProfileActivity, LoginActivity::class.java)
        dialog?.dismiss()
        startActivity(intent)
        finishAffinity()
    }

    private fun initView() {
        profileVM = ViewModelProvider(this).get(ProfileVM::class.java)
    }

    private fun iniObserver() {
        profileVM.response.observe(this) { response ->
            if (response != null) {
                binding.apply {
                    response.data?.let {
                        tvBillAmount.text = getString(R.string.rupees) + response.data.billAmount
                        tvAccountName.text = response.data.accountName
                        tvAccountNo.text = response.data.accountNo
                        tvMobile.text = response.data.mobileNumber
                        tvEmail.text = response.data.email
                        tvMeterNo.text = response.data.meterSerialNumber
                        tvAddress.text = response.data.address
                        tvDueDate.text = response.data.rechargeDate
                    }

                }
            }
        }
    }


    private fun getData() {
        val params: HashMap<String, Any> = header
        profileVM.getProfileApi(params)
    }


    private fun openDialog() {
        rechargeMeterAPI()
        chargeDialog = Dialog(context)
        ratesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_current_rates,
            null,
            false
        )
        chargeDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        chargeDialog.setContentView(ratesBinding.root)
        chargeDialog.setCancelable(false)
        chargeDialog.show()
        val window: Window? = chargeDialog.window
        val wlp: WindowManager.LayoutParams = window!!.attributes


        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        if (window != null) {
            window.attributes = wlp
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        ratesBinding.ivCancel.setOnClickListener {
            chargeDialog.dismiss()
        }

        ratesBinding.btnSubmit.setOnClickListener {
            chargeDialog.dismiss()
        }

    }

    private fun rechargeMeterAPI() {
        Global.initRetrofit().getTariffRate(header)
            ?.enqueue(object : Callback<TariffRateModel?> {
                override fun onResponse(
                    call: Call<TariffRateModel?>, response: Response<TariffRateModel?>
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.success == true) {
                        Log.d(TAG, "onResponse rechargeMeterAPI: " + response.body()!!.message)
                        if (response.body()!!.data!!.ebRate != null &&
                            response.body()!!.data!!.ebRate != ""
                        ) {
                            ratesBinding.tvEdRate.text = response.body()!!.data!!.ebRate
                        }

                        if (response.body()!!.data!!.dgRate != null &&
                            response.body()!!.data!!.dgRate != ""
                        ) {
                            ratesBinding.tvDgRate.text = response.body()!!.data!!.dgRate
                        }

                        if (response.body()!!.data!!.maintenanceCharge != null &&
                            response.body()!!.data!!.maintenanceCharge != ""
                        ) {
                            ratesBinding.tvMaintenanceRate.text =
                                response.body()!!.data!!.maintenanceCharge
                        } else {
                            ratesBinding.tvMaintenanceRate.text =
                                "₹ 0"
                        }

                        if (response.body()!!.data!!.waiverCharges != null &&
                            response.body()!!.data!!.waiverCharges != "") {
                            ratesBinding.tvWaiverCharges.text =
                                "₹ ${response.body()!!.data!!.waiverCharges}"
                        } else {
                            ratesBinding.tvWaiverCharges.text =  "₹ 0"
                        }

                        if (response.body()!!.data!!.waterCharges != null && response.body()!!.data!!.waterCharges != "") {
                            ratesBinding.tvWaterCharges.text =
                                "₹ ${response.body()!!.data!!.waterCharges}"
                        } else {
                            ratesBinding.tvWaterCharges.text = "₹ 0"
                        }

                        if (response.body()!!.data!!.sewageCharges != null &&
                            response.body()!!.data!!.sewageCharges != "") {
                            ratesBinding.tvSewageCharge.text =
                                "₹ ${response.body()!!.data!!.sewageCharges}"
                        } else {
                            ratesBinding.tvSewageCharge.text =  "₹ 0"
                        }

                        if (response.body()!!.data!!.chequeCharges != null &&
                            response.body()!!.data!!.chequeCharges != "") {
                            ratesBinding.tvChequePenalty.text =
                                "₹ ${response.body()!!.data!!.chequeCharges}"
                        } else {
                            ratesBinding.tvChequePenalty.text = "₹ 0"
                        }

                        if (response.body()!!.data!!.otherCharges != null &&
                            response.body()!!.data!!.otherCharges != "") {
                            ratesBinding.tvOtherCharges.text =
                                "₹ ${response.body()!!.data!!.otherCharges}"
                        } else {
                            ratesBinding.tvOtherCharges.text = "₹ 0"
                        }
                    } else {
                        Log.d(TAG, "onResponse rechargeMeterAPI: " + response.body())
                    }
                }

                override fun onFailure(call: Call<TariffRateModel?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}