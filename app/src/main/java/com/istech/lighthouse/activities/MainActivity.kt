package com.istech.lighthouse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityMainBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.viewmodel.dashboard.DashboardVM
import java.util.*
import android.content.pm.PackageManager

import android.content.pm.PackageInfo
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activity: MainActivity
    private lateinit var context: Context
    private lateinit var dashboardVM: DashboardVM
    private val TAG = "MainActivity"

    //    private lateinit var drawer: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activity = this
        context = activity
        handleClick()
        initView()
        getData()
        iniObserver()
        showVersionNumber()
        pullToRefresh()
    }

    private fun initView() {
        dashboardVM = ViewModelProvider(this).get(DashboardVM::class.java)
    }

    private fun getData() {
        val params: HashMap<String, Any> = header
        dashboardVM.getDashboardApi(params)
    }

    private fun iniObserver() {
        binding.tvName.setText(sessionManager.user.data.fullName)

        dashboardVM.response.observe(this) { response ->
            if (response != null) {
                binding.apply {
                    response.data?.let {
                        if (response.data.isDeleted){
                            logout()
                        }else{
                            val notificationNumber = response.data.notificationCount
                            if (notificationNumber > 0) {
                                tvNotificationCount.isVisible = true
                                tvNotificationCount.text = notificationNumber.toString()
                            } else {
                                tvNotificationCount.isVisible = false
                            }
                            tvName.text = response.data.consumerName
                            tvLastUpdated.text = "Last Update : " + response.data.lastUpdatedDate
                            tvBalance.text = getString(R.string.rupees) + response.data.balance.toString()
                        }
                    } ?: run {
                        logout()
                    }
                }
            }else{
                logout()
            }
        }
        binding.flNotification.setOnClickListener {
            val intent = Intent(this@MainActivity, NotificationActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun logout() {
        sessionManager.clear()
        val intent = Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    private fun showVersionNumber() {
        //For only local build version
        val pm = applicationContext.packageManager
        val pkgName = applicationContext.packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val ver = pkgInfo!!.versionName

        //For release version
//        val ver = "Version " + BuildConfig.VERSION_NAME
        val version = "Version $ver"
        binding.version.text = version
    }

    private fun pullToRefresh() {
        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.refresh_to_pull)
        pullToRefresh.setOnRefreshListener {
            getData()
            iniObserver() //your code
            Log.d(TAG, "pullToRefresh: ")
            pullToRefresh.isRefreshing = false
        }
    }



    private fun handleClick() {
        binding.dash.apply {
            binding.statics.setOnClickListener {
                val intent = Intent(this@MainActivity, StaticsActivity::class.java)
                startActivity(intent)
            }
            binding.recharge.setOnClickListener {
                val intent = Intent(this@MainActivity, RechargeAcivity::class.java)
                startActivity(intent)
            }
//            binding.recharge.setOnClickListener {
//
//                val intent = Intent(this@MainActivity, RechargeActivity2::class.java)
//                startActivity(intent)
//            }
            binding.report.setOnClickListener {
                val intent = Intent(this@MainActivity, ReportActivity::class.java)
                startActivity(intent)
            }
            binding.notification.setOnClickListener {
                val intent = Intent(this@MainActivity, NotificationActivity::class.java)
                resultLauncher.launch(intent)
//                startActivity(intent)
            }
            binding.account.setOnClickListener {
                val intent = Intent(this@MainActivity, MyProfileActivity::class.java)
                startActivity(intent)
            }
            binding.contactUs.setOnClickListener {
                val intent = Intent(this@MainActivity, ContactUsActivity::class.java)
                startActivity(intent)
            }

            binding.ivNav.setOnClickListener {
                val drawer = binding.drawerLayout
                drawer.openDrawer(GravityCompat.START)
            }
            
        }
    }


    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // There are no request codes
//                val data: Intent? = result.data
//            }
            Log.d(TAG, "onActivityResult: ")
            iniObserver()

        }
}
