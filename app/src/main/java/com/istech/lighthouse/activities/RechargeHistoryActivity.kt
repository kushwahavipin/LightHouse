package com.istech.lighthouse.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.istech.lighthouse.R
import com.istech.lighthouse.adapter.RecentPaymentAdapter
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.databinding.ActivityRechargeHistoryBinding
import com.istech.lighthouse.utils.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class RechargeHistoryActivity : BaseActivity() {
   lateinit var binding: ActivityRechargeHistoryBinding

    private var adapter = RecentPaymentAdapter()

   lateinit var context: Context
   lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        activity=this
        binding=DataBindingUtil.setContentView(this,R.layout.activity_recharge_history)

        initView()
    }
    private fun initView() {

            binding.ivBack.setOnClickListener {
                onBackPressed()
            }
         //   binding.rvRecentPayments.adapter = adapter
//            tvTitle.text = "Recharge History"
//        }
        binding.rvRecentPayments.adapter=adapter;
        binding.etFromDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var dd="";
                if (dayOfMonth<10){
                    dd="0"+dayOfMonth
                }else{
                    dd=""+dayOfMonth
                }
                var mm="";
                if (dayOfMonth<10){
                    mm="0"+(monthOfYear+1)
                }else{
                    mm=""+(monthOfYear+1)

                }
                var yyyy=""+year
                var dates=dd+"-"+mm+"-"+yyyy;
                binding.etFromDate.setText(dates)

                // Display Selected date in textbox
                // lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)

            }, year, month, day)
            dpd.datePicker.maxDate=System.currentTimeMillis()

            dpd.show()

        }
        binding.etToDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var dd="";
                if (dayOfMonth<10){
                    dd="0"+dayOfMonth
                }else{
                    dd=""+dayOfMonth
                }
                var mm="";
                if (dayOfMonth<10){
                    mm="0"+(monthOfYear+1)
                }else{
                    mm=""+(monthOfYear+1)

                }
                var yyyy=""+year
                var dates=dd+"-"+mm+"-"+yyyy;
                binding.etToDate.setText(dates)

                // Display Selected date in textbox
                // lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)

            }, year, month, day)
            dpd.datePicker.maxDate=System.currentTimeMillis()

            dpd.show()

        }
        
        binding.ivSearch.setOnClickListener {
            fromDate=binding.etFromDate.text.toString()
            toDate=binding.etToDate.text.toString()
            getRecentData()
        }
        
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        fromDate=currentDate
        toDate=currentDate
        binding.etFromDate.setText(currentDate)
        binding.etToDate.setText(currentDate)


        getRecentData()

    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    var fromDate="12-12-2021"
    var toDate="20-12-2021"
    private fun getRecentData(){
        binding.progressBar.visibility= View.VISIBLE
        val map = HashMap<String, Any>()
        map.put("fromDate",fromDate)
        map.put("toDate",toDate)
        disposable.add(
            Global.initRetrofit().getRechargeHistory(header,map).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io()).subscribe{ home, throwable ->
                    binding.progressBar.visibility= View.GONE
                    if (home!=null){
                    adapter.updateData(home.data)
                    if (home.data.size==0){
                        binding.rvRecentPayments.visibility= View.GONE
                        binding.tvNoData.visibility= View.VISIBLE
                    }else{
                        binding.rvRecentPayments.visibility= View.VISIBLE
                        binding.tvNoData.visibility= View.GONE
                    }
                }
            }
        )
    }
}