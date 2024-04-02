package com.istech.lighthouse.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.istech.lighthouse.R
import com.istech.lighthouse.adapter.NotificationAdapter
import com.istech.lighthouse.api.Global
import com.istech.lighthouse.databinding.ActivityNotificationBinding
import com.istech.lighthouse.model.mark_as_read.MarkAsRead
import com.istech.lighthouse.utils.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NotificationActivity : BaseActivity(), NotificationAdapter.ClickedOnCard {
    private lateinit var binding: ActivityNotificationBinding
    private var adapter = NotificationAdapter(this)
    private val TAG = "NotificationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        initView()
    }

    private fun initView() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            rvNotification.adapter = adapter
        }
        getNotificationData()
    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun getNotificationData() {
        binding.progressBar.visibility = View.VISIBLE
        disposable.add(
            Global.initRetrofit().getNotificationList(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe { home, throwable ->
                    if (home != null) {
                        binding.rvNotification.adapter = adapter;
                        adapter.updateData(home.data)
                        if (home.data.size == 0) {
                            binding.rvNotification.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvNotification.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }
        )
    }

    override fun cardViewSelected(notificationId: Int) {
        callMarkAsReadAPI(notificationId)
    }

    private fun callMarkAsReadAPI(notificationID: Int) {
        val map = HashMap<String, Any>()
        map["notificationID"] = notificationID

        Global.initRetrofit().getMarkAsRead(header, map)?.enqueue(object : Callback<MarkAsRead?> {
                override fun onResponse(call: Call<MarkAsRead?>, response: Response<MarkAsRead?>) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.success) {
                        Log.d(TAG, "onResponse getMarkAsRead: " + response.body()!!.message)
//                        Toast.makeText(
//                            applicationContext,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
                    } else {
                        Log.d(TAG, "onResponse getMarkAsRead: " + response.body())
                    }
                }
                override fun onFailure(call: Call<MarkAsRead?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


}