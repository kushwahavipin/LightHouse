package com.istech.lighthouse.activities

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityOnGoingReportBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.viewmodel.ongoingdata.OnGoingDataVM
import java.util.HashMap

class OnGoingReportActivity : BaseActivity() {
    private lateinit var binding: ActivityOnGoingReportBinding

    private lateinit var activity: OnGoingReportActivity
    private lateinit var context: Context
    private lateinit var ongoingVM: OnGoingDataVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_going_report)
        handleClick()

        activity = this
        context = activity
        initView()
        getData()
        iniObserver()
    }

    private fun initView() {
        ongoingVM = ViewModelProvider(this).get(OnGoingDataVM::class.java)
    }

    private fun iniObserver() {
        ongoingVM.response.observe(this) { response ->
            if (response != null) {
                binding.apply {
                    tvVoltage1.text = "V1 : "+response.data.voltage1
                    tvVoltage2.text = "V2 : "+response.data.voltage2
                    tvVoltage3.text ="V3 : "+ response.data.voltage3
                    tvWatt1.text = "W1 : "+response.data.watts1
                    tvWatt2.text = "W2 : "+response.data.watts2
                    tvWatt3.text = "W3 : "+response.data.watts3
                    tvActivePower.text =response.data.activePower
                    tvApparentPower.text =response.data.apparentPower
                    tvPowerFactor.text =response.data.powerFactor
                    tvKwhGrid.text =response.data.kwhGrid
                    tvKwahGrid.text =response.data.kvahGrid
                    tvKwhDg.text =response.data.kwhDG
                    tvKwahDg.text =response.data.kvahDG

                    /*i1Value.text = response.data.i1
                    i2Value.text = response.data.i2
                    i3Value.text = response.data.i3
                    w1Value.text = response.data.w1
                    w2Value.text = response.data.w2
                    w3Value.text = response.data.w3
                    vA1Value.text = response.data.vA1
                    vA2Value.text = response.data.vA2
                    vA3Value.text = response.data.vA3
                    vaR1Value.text = response.data.vaR1
                    vaR2Value.text = response.data.vaR2
                    pF1Value.text = response.data.pF1
                    pF2Value.text = response.data.pF2
                    pF3Value.text = response.data.pF3
                    vValue.text = response.data.v
                    iValue.text = response.data.i
                    wValue.text = response.data.w
                    vaValue.text = response.data.va
                    varrValue.text = response.data.varr
                    pfValue.text = response.data.pf
                    frValue.text = response.data.fr.toString() + ""
                    rtcValue.text = response.data.rtc
                    msnoValue.text = response.data.msno*/
                }
            }
        }
    }

    private fun getData() {
        val params: HashMap<String, Any> = header

        ongoingVM.getOngoingColumnValuesApi(params)
    }

    private fun handleClick() {

            binding.ivBack.setOnClickListener {
                onBackPressed()
            }

    }
}