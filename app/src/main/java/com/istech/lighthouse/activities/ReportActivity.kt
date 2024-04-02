package com.istech.lighthouse.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityReportBinding
import com.istech.lighthouse.utils.BaseActivity

class ReportActivity : BaseActivity() {
    private lateinit var binding: ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report)
        initView()
    }

    private fun initView() {

            binding.ivBack.setOnClickListener {
                onBackPressed()
            }
//            tvTitle.text = "Reports"

//        }
        binding.apply {
            binding.ongoingCard.setOnClickListener {
                val intent = Intent(this@ReportActivity, OnGoingReportActivity::class.java)
                startActivity(intent)

            }
            binding.rechargeHistory.setOnClickListener {
                val intent = Intent(this@ReportActivity, RechargeHistoryActivity::class.java)
                startActivity(intent)

            }
            binding.monthWiseCard.setOnClickListener {
                val intent = Intent(this@ReportActivity, MonthWiseActivity::class.java)
                startActivity(intent)

            }
            binding.comparativeCard.setOnClickListener {
                val intent = Intent(this@ReportActivity, ComparativeActivity::class.java)
                startActivity(intent)

            }
            binding.dateWiseCard.setOnClickListener {
                val intent = Intent(this@ReportActivity, DateWiseConsumptionActivity::class.java)
                startActivity(intent)

            }

            binding.dailyWiseData.setOnClickListener {
                val intent = Intent(this@ReportActivity, DailyDataActivity::class.java)
                startActivity(intent)
            }
        }
    }
}