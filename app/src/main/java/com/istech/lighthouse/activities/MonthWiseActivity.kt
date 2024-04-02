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
import com.istech.lighthouse.databinding.ActivityMonthWiseBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.utils.Commn
import com.whiteelephant.monthpicker.MonthPickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList
class MonthWiseActivity : BaseActivity() {
    lateinit var binding: ActivityMonthWiseBinding


    lateinit var context: Context
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        activity=this
        binding= DataBindingUtil.setContentView(this,R.layout.activity_month_wise)
        initView()
    }
    private fun initView() {
        binding.rlHeader.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            //   binding.rvRecentPayments.adapter = adapter
            tvTitle.text = "Month Wise"
            binding.etFromDate.setText("2022")
        }

        binding.etFromDate.setOnClickListener {

            val obj  = MonthPickerDialog.Builder(this,MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->
                binding.etFromDate.setText(selectedYear.toString())
            },2022,4)

            obj.setMinYear(2018)
            obj.setMaxYear(2030)

            obj.showYearOnly()
            obj.build().show()
        }

//        binding.etToDate.setOnClickListener {
//
//            val c = Calendar.getInstance()
//            val year = c.get(Calendar.YEAR)
//            val month = c.get(Calendar.MONTH)
//            val day = c.get(Calendar.DAY_OF_MONTH)
//
//
//            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//
//                binding.etToDate.setText(year)
//
//                // Display Selected date in textbox
//                // lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
//
//            }, year, month, day)
//            dpd.datePicker.maxDate=System.currentTimeMillis()
//
//            dpd.show()
//
//        }

        binding.ivSearch.setOnClickListener {
            fromDate=binding.etFromDate.text.toString()
            //toDate=binding.etToDate.text.toString()
            getRecentData()
        }
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val currentDate = sdf.format(Date())
//
//        fromDate=currentDate
//      //  toDate=currentDate
//        binding.etFromDate.setText(currentDate)
//      //  binding.etToDate.setText(currentDate)

        getRecentData()

    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    var fromDate="2022"
   // var toDate="20-12-2021"
    var  entry1=ArrayList<Entry>()
    var  entry2=ArrayList<Entry>()
    var  entry3=ArrayList<Entry>()
    var  entry4=ArrayList<Entry>()


    private fun showLogoutDialog(message:String) {
        val ad = androidx.appcompat.app.AlertDialog.Builder(this)
        ad.setTitle("")
        ad.setMessage(message)
        ad.setNegativeButton("Ok") { dialog: DialogInterface, i: Int -> dialog.dismiss() }
        ad.create()
        ad.show()
    }

    private fun getRecentData(){
        val map = HashMap<String, Any>()
        map.put("year",fromDate)
        //map.put("toDate",toDate)
        Commn.showProgress(context)
        disposable.add(
            Global.initRetrofit().getMonthWiseData(header,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe{ home, throwable ->
                    Commn.hideProgress()
                    entry1= ArrayList()
                    entry2=ArrayList()
                    entry3=ArrayList()
                    entry4=ArrayList()
                    binding.barChart1.clear()
                    val label = java.util.ArrayList<String>()
                    label.add("")

                    if (home!=null){
                        if(home.success == false){
                            showLogoutDialog(home.message)
                        }else {
                            if (home.data.size == 0) {
                                binding.barChart1.visibility = View.GONE
                            } else {
                                binding.barChart1.visibility = View.VISIBLE
                                val entries: ArrayList<BarEntry> = ArrayList()
                                var xAxisList: ArrayList<String> = ArrayList()
                                val labels = arrayOf(
                                    "Grid(${sessionManager.user.data.billingType})",
                                    "DG(${sessionManager.user.data.billingType})"
                                )
                                val colors: ArrayList<Int> = ArrayList()

                                colors.add(Color.GREEN)
                                colors.add(Color.RED)

                                var index = 0;
                                for (data in home.data) {
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

                                var barDataSet = BarDataSet(entries, "")
                                barDataSet.colors = colors
                                barDataSet.stackLabels = labels

                                val barData = BarData(barDataSet)
                                binding.barChart1.data = barData
                                binding.barChart1.setTouchEnabled(false)
                                binding.barChart1.description.isEnabled = false
                                binding.barChart1.setFitBars(true)
                                binding.barChart1.invalidate()
                                binding.barChart1.xAxis.setLabelCount(xAxisList.count());
                                binding.barChart1.xAxis.valueFormatter =
                                    IndexAxisValueFormatter(xAxisList)

                            }

                        }

                    }
                }
        )
    }
}