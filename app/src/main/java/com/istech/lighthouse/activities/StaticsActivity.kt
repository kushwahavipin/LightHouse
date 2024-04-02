package com.istech.lighthouse.activities

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityStaticsBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.viewmodel.statics.StaticsVM
import java.util.HashMap

class StaticsActivity : BaseActivity() {
    private lateinit var binding: ActivityStaticsBinding
    private lateinit var activity: StaticsActivity
    private lateinit var context: Context
    private lateinit var staticsVM: StaticsVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statics)
        activity = this
        context = activity
        handleClick()
        initView()
        getData()
        iniObserver()
    }

    private fun handleClick() {
//        binding.apply {
       binding.ivBack.setOnClickListener { onBackPressed() }


//        }
    }

    private fun initView() {
        staticsVM = ViewModelProvider(this).get(StaticsVM::class.java)
        binding.tvTitle.text = "Statistics"
    }

    private fun iniObserver() {
        staticsVM.response.observe(this) { response ->
            if (response != null) {
                binding.apply {
                    tvLastUpdated.text = response.data.updatedOn
                    tvUnitNo.text = response.data.meterSerialNumber.toString()
                    tvConsumerName.text = response.data.consumerName
                    tvConsumerNo.text = response.data.consumerNo
                    tvRechargedOn.text = response.data.rechargeOn
//                    tvGridStarted.text = response.data.gridStarted
                    tvGetReading.text = response.data.gridReading
                    tvDGReading.text = response.data.dgReading
                    tvTodayConsumption.text = response.data.todayConsumption.toString()
                    tvCurrentMonth.text = response.data.currentMonthConsumption.toString()
                    tvBalance.text = getString(R.string.rupees) + response.data.balance.toString()


                    val gridReading = response.data.gridReading;
                    val dgReading = response.data.dgReading;


/*
                    val NoOfEmp = ArrayList<PieEntry>()

                    NoOfEmp.add(PieEntry(945f, "2008"))
                    NoOfEmp.add(PieEntry(1040f, "2009"))
                    val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

                    dataSet.setDrawIcons(false)
                    dataSet.sliceSpace = 3f
                    dataSet.iconsOffset = MPPointF(0F, 40F)
                    dataSet.selectionShift = 5f
                    dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

                    val data = PieData(dataSet)
                    data.setValueTextSize(20f)
                    data.setValueTextColor(Color.WHITE)
                    pieChart.data = data
                    pieChart.highlightValues(null)
                    pieChart.invalidate()
                    pieChart.animateXY(5000, 5000)
*/

                    val listPie = ArrayList<PieEntry>()
                    val listColors = ArrayList<Int>()
                    listPie.add(PieEntry(dgReading.toFloat(), "DG Value"))
                    listColors.add(resources.getColor(R.color.dark_blue))
                    listPie.add(PieEntry(gridReading.toFloat(), "Grid Value"))
                    listColors.add(resources.getColor(R.color.orange))
                    val pieDataSet = PieDataSet(listPie, "")
                    pieDataSet.colors = listColors

                    val pieData = PieData(pieDataSet)
                    pieData.setValueTextSize(20f)
                    pieChart.data = pieData

                    pieChart.setUsePercentValues(false)
                    pieChart.isDrawHoleEnabled = false
                    pieChart.description.isEnabled = false
                    pieChart.setEntryLabelColor(R.color.white)
                    pieChart.animateY(1400, Easing.EaseInOutQuad)


                }
            }
        }
    }


    private fun getData() {
        val params: HashMap<String, Any> = header
        staticsVM.getStaticsApi(params)
    }

}