package com.istech.lighthouse.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.istech.lighthouse.R
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.databinding.ActivityComparativeBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.utils.Commn
import com.whiteelephant.monthpicker.MonthPickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ComparativeActivity : BaseActivity() {
    lateinit var binding: ActivityComparativeBinding
    lateinit var context: Context
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        activity=this
        binding= DataBindingUtil.setContentView(this,R.layout.activity_comparative)
        initView()
    }
    private fun initView() {
        binding.rlHeader.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            //   binding.rvRecentPayments.adapter = adapter
            tvTitle.text = "Comparative"
        }
        binding.etFromDate.setOnClickListener {
            val obj  = MonthPickerDialog.Builder(this,
                MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->
                    fromYear = (selectedYear).toString()
                    fromMonth = (selectedMonth+1).toString()
                    var str = fromMonth.plus(" - ").plus(fromYear)
                    binding.etFromDate.setText(str)
            },2022,4)

            obj.setMinYear(2018)//lower limit
            obj.setMaxYear(2030)//upper limit
            val sdf = SimpleDateFormat("MM")
            obj.setActivatedMonth(fromMonth.toInt()-1)

           // obj.showYearOnly()
            obj.build().show()

        }
        binding.etToDate.setOnClickListener {
            val obj  = MonthPickerDialog.Builder(this,
                MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->
                    toYear = selectedYear.toString()
                    toMonth = (selectedMonth+1).toString()
                    var str = toMonth.plus(" - ").plus(toYear)
                    binding.etToDate.setText(str)
                },2022,4)

            obj.setMinYear(2018)//lower limit
            obj.setMaxYear(2030)//upper limit
            obj.setActivatedMonth(toMonth.toInt()-1)
            // obj.showYearOnly()
            obj.build().show()
        }
        binding.ivSearch.setOnClickListener {
            getRecentData()
        }
        val sdf = SimpleDateFormat("MM")
        val yy = SimpleDateFormat("yyyy")
        val currentDate = sdf.format(Date())
        val currentYear = yy.format(Date())
         fromYear=currentYear.toString()
         toYear=currentYear.toString()
         fromMonth = currentDate.toString()
         toMonth = currentDate.toString()
        var str = fromMonth.plus(" - ").plus(fromYear)
        binding.etFromDate.setText(str)
        binding.etToDate.setText(str)
        getRecentData()
    }

    private val disposable: CompositeDisposable = CompositeDisposable()
    var fromYear="2022"
    var toYear="2022"
    var fromMonth = "01"
    var toMonth = "01"
    var  entry1=ArrayList<Entry>()
    var  entry2=ArrayList<Entry>()
    var  entry3=ArrayList<Entry>()
    var  entry4=ArrayList<Entry>()
    private fun getRecentData(){
        val map = HashMap<String, Any>()
        map["fromMonth"] = fromMonth
        map["fromYear"] = fromYear
        map["toMonth"] = toMonth
        map["toYear"] = toYear

        Commn.showProgress(context)
        disposable.add(
            Global.initRetrofit().getComparativeData(header,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe{ home, _ ->
                    Commn.hideProgress()
                    entry1= ArrayList()
                    entry2=ArrayList()
                    entry3=ArrayList()
                    entry4=ArrayList()
                    binding.barChart1.clear()
                    binding.barChart2.clear()

                    if (home!=null){
                        if(home.success == false){
                            showLogoutDialog(home.message)
                        }else {
                            if (home.data.fromData.size == 0) {
                                binding.barChart1.visibility = View.GONE
                            } else {
                                binding.barChart1.visibility = View.VISIBLE
                                val entries: ArrayList<BarEntry> = ArrayList()
                                val labels = arrayOf(
                                    "Grid(${sessionManager.user.data.billingType})",
                                    "DG(${sessionManager.user.data.billingType})"
                                )
                                val colors: ArrayList<Int> = ArrayList()
                                val xAxisList: ArrayList<String> = ArrayList()

                                colors.add(Color.GREEN)
                                colors.add(Color.RED)

                                var index = 0;
                                for (data in home.data.fromData) {
                                    val x = data.gridValue;
                                    val z = data.dgValue;
                                    xAxisList.add(data.lable)
                                    entries.add(
                                        BarEntry(
                                            index.toFloat(),
                                            floatArrayOf(x.toFloat(), z.toFloat())
                                        )
                                    )
                                    index += 1;
                                }

                                val barDataSet = BarDataSet(entries, "")
                                barDataSet.colors = colors
                                barDataSet.stackLabels = labels

                                val barData = BarData(barDataSet)
                                barData.barWidth = 0.1f;

                                binding.barChart1.data = barData
                                binding.barChart1.description.isEnabled = false
                                binding.barChart1.setFitBars(true)
                                binding.barChart1.invalidate()
                                binding.barChart1.setTouchEnabled(false)
                                binding.barChart1.xAxis.valueFormatter =
                                    IndexAxisValueFormatter(xAxisList)
                            }

                            if (home.data.toData.size == 0) {
                                binding.barChart2.visibility = View.GONE
                            } else {
                                binding.barChart2.visibility = View.VISIBLE
                                val entries: ArrayList<BarEntry> = ArrayList()
                                val labels = arrayOf(
                                    "Grid(${sessionManager.user.data.billingType})",
                                    "DG(${sessionManager.user.data.billingType})"
                                )
                                val colors: ArrayList<Int> = ArrayList()
                                val xAxisList: ArrayList<String> = ArrayList()

                                colors.add(Color.GREEN)
                                colors.add(Color.RED)

                                var index = 0;
                                for (data in home.data.toData) {
                                    val x = data.gridValue;
                                    val z = data.dgValue;
                                    xAxisList.add(data.lable)
                                    entries.add(
                                        BarEntry(
                                            index.toFloat(),
                                            floatArrayOf(x.toFloat(), z.toFloat())
                                        )
                                    )
                                    index += 1;
                                }

                                val barDataSet = BarDataSet(entries, "")
                                barDataSet.colors = colors
                                barDataSet.stackLabels = labels

                                val barData = BarData(barDataSet)
                                barData.barWidth = 0.1f;
                                binding.barChart2.data = barData
                                binding.barChart2.description.isEnabled = false
                                binding.barChart2.setFitBars(true)
                                binding.barChart2.invalidate()
                                binding.barChart2.setTouchEnabled(false)
                                binding.barChart2.xAxis.valueFormatter =
                                    IndexAxisValueFormatter(xAxisList)

                            }
                        }
                    }
                }
        )
    }

    private fun showLogoutDialog(message:String) {
        val ad = androidx.appcompat.app.AlertDialog.Builder(this)
        ad.setTitle("")
        ad.setMessage(message)
        ad.setNegativeButton("Ok") { dialog: DialogInterface, i: Int -> dialog.dismiss() }
        ad.create()
        ad.show()
    }
}