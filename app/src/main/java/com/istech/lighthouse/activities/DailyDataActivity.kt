package com.istech.lighthouse.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.axes.Linear
import com.anychart.core.cartesian.series.Bar
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.*
import com.github.mikephil.charting.data.*
import com.istech.lighthouse.R
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.utils.Commn
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.istech.lighthouse.databinding.ActivityDailyDataBinding


class DailyDataActivity : BaseActivity() {
    private lateinit var binding: ActivityDailyDataBinding
    var fromDate = "12-12-2021"

    lateinit var context: Context
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activity = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daily_data)
        handleCLick()
        initView()
    }

    private fun initView() {
        // initChart()
        binding.etFromDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { _, _, monthOfYear, dayOfMonth ->

                    var dd = "";
                    dd = if (dayOfMonth < 10) {
                        "0$dayOfMonth"
                    } else {
                        "" + dayOfMonth
                    }
                    var mm = "";
                    mm = if (dayOfMonth < 10) {
                        "0" + (monthOfYear + 1)
                    } else {
                        "" + (monthOfYear + 1)

                    }
                    val yyyy = "" + year
                    val dates = "$dd-$mm-$yyyy";
                    binding.etFromDate.setText(dates)

                    // Display Selected date in textbox
                    // lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)

                },
                year,
                month,
                day
            )
            dpd.datePicker.maxDate = System.currentTimeMillis()
            dpd.show()

        }
        binding.ivSearch.setOnClickListener {
            fromDate=binding.etFromDate.text.toString()
            getRecentData()
        }
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        fromDate = currentDate
        binding.etFromDate.setText(currentDate)

        println(" C DATE is  $currentDate")
        getRecentData()

    }

    private fun handleCLick() {

        binding.rlHeader.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            tvTitle.text = "Daily-Data"
        }
    }

    private fun initChart() {
        binding.anyChartView.setProgressBar(binding.progressBar)
        val barChart: Cartesian = AnyChart.bar()

        barChart.animation(true)

        barChart.padding(10.0, 20.0, 5.0, 20.0)

        barChart.yScale().stackMode(ScaleStackMode.VALUE)

        barChart.yAxis(0).labels().format(
            "function() {\n" +
                    "    return Math.abs(this.value).toLocaleString();\n" +
                    "  }"
        )

        barChart.yAxis(0.0).title("Revenue in Dollars")

        barChart.xAxis(0.0).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP)

        val xAxis1: Linear = barChart.xAxis(1.0)
        xAxis1.enabled(true)
        xAxis1.orientation(Orientation.RIGHT)
        xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP)

        barChart.title("")

        barChart.interactivity().hoverMode(HoverMode.BY_X)

        barChart.tooltip()
            .title(false)
            .separator(false)
            .displayMode(TooltipDisplayMode.SEPARATED)
            .positionMode(TooltipPositionMode.POINT)
            .useHtml(true)
            .fontSize(12.0)
            .offsetX(5.0)
            .offsetY(0.0)
            .format(
                ("function() {\n" +
                        "      return '<span style=\"color: #D9D9D9\">$</span>' + Math.abs(this.value).toLocaleString();\n" +
                        "    }")
            )

        val seriesData: MutableList<DataEntry> = ArrayList()
        seriesData.add(CustomDataEntry("Nail polish", 5376, -229))
        seriesData.add(CustomDataEntry("Eyebrow pencil", 10987, -932))
        seriesData.add(CustomDataEntry("Rouge", 7624, -5221))
        seriesData.add(CustomDataEntry("Lipstick", 8814, -256))
        seriesData.add(CustomDataEntry("Eyeshadows", 8998, -308))
        seriesData.add(CustomDataEntry("EyeBarr", 9321, -432))
        seriesData.add(CustomDataEntry("Foundation", 8342, -701))
        seriesData.add(CustomDataEntry("Lip gloss", 6998, -908))
        seriesData.add(CustomDataEntry("Mascara", 9261, -712))
        seriesData.add(CustomDataEntry("Shampoo", 5376, -9229))
        seriesData.add(CustomDataEntry("Hair conditioner", 10987, -13932))
        seriesData.add(CustomDataEntry("Body lotion", 7624, -10221))
        seriesData.add(CustomDataEntry("Shower gel", 8814, -12256))
        seriesData.add(CustomDataEntry("Soap", 8998, -5308))
        seriesData.add(CustomDataEntry("Eye fresher", 9321, -432))
        seriesData.add(CustomDataEntry("Deodorant", 8342, -11701))
        seriesData.add(CustomDataEntry("Hand cream", 7598, -5808))
        seriesData.add(CustomDataEntry("Foot cream", 6098, -3987))
        seriesData.add(CustomDataEntry("Night cream", 6998, -847))
        seriesData.add(CustomDataEntry("Day cream", 5304, -4008))
        seriesData.add(CustomDataEntry("Vanilla cream", 9261, -712))

        val set: Set = com.anychart.data.Set.instantiate()
        set.data(seriesData)
        val series1Data: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Data: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")

        val series1: Bar = barChart.bar(series1Data)
        series1.name("Grid")
            .color("HotPink")
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)

        val series2: Bar = barChart.bar(series2Data)
        series2.name("DG")
        series2.tooltip()
            .position("left")
            .anchor(Anchor.RIGHT_CENTER)

        barChart.legend().enabled(true)
        barChart.legend().inverted(true)
        barChart.legend().fontSize(13.0)
        barChart.legend().padding(0.0, 0.0, 20.0, 0.0)

        binding.anyChartView.setChart(barChart)
    }

    private class CustomDataEntry internal constructor(
        x: String?,
        value: Number?,
        value2: Number?
    ) : ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
        }
    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    var  entry1=ArrayList<Entry>()
    var  entry2=ArrayList<Entry>()

    private fun getRecentData(){
        val map = HashMap<String, Any>()
        //map["date"] = fromDate
        //map.put("toDate",toDate)
        Commn.showProgress(context)

        disposable.add(
            Global.initRetrofit().getDailyData(header,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe{ home, _ ->
                    Commn.hideProgress()
                    entry1= ArrayList()
                    entry2=ArrayList()

                    binding.barChart1.clear()
                    val label = java.util.ArrayList<String>()
                    label.add("")
                    if (home!=null){
                        if (home.success == false){
                            showLogoutDialog(home.message)
                        }else {
                            if (home.data.size == 0) {
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

                                val barDataSet = BarDataSet(entries, "")
                                barDataSet.colors = colors
                                barDataSet.stackLabels = labels

                                val barData = BarData(barDataSet)
                                binding.barChart1.data = barData
                                binding.barChart1.description.isEnabled = false
                                binding.barChart1.setFitBars(true)
                                binding.barChart1.invalidate()
                                binding.barChart1.xAxis.setLabelCount(xAxisList.count());
                                binding.barChart1.setTouchEnabled(false)
                                binding.barChart1.xAxis.valueFormatter =
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