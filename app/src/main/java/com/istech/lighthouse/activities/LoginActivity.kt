package com.istech.lighthouse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityLoginBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.utils.Commn
import com.istech.lighthouse.utils.FastClickUtil
import com.istech.lighthouse.viewmodel.LoginVM
import java.util.HashMap

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAwesomeValidation: AwesomeValidation
    private lateinit var activity: LoginActivity
    private lateinit var context: Context
    private lateinit var loginVM: LoginVM
    private val TAG = "LoginActivity"
    var deviceToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        activity = this
        context = activity
        firebaseToken()
        initView()
        handleClick()
        initObserver()
    }

    private fun initObserver() {
        loginVM.loginResponse.observe(this) { loginModel ->
            if (loginModel != null) {
                Commn.hideProgress()
                Commn.myToast(context, loginModel.message + "")
                if (loginModel.success) {
                    sessionManager.saveRazorPayKey(loginModel.data.razorpayKey)
                    sessionManager.saveLogin(true)
                    sessionManager.saveUser(loginModel)
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }
    }

    private fun initView() {
        mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)

        loginVM = ViewModelProvider(this).get(LoginVM::class.java)
    }

    private fun handleClick() {
        binding.apply {
            btLogin.setOnClickListener {
                if (FastClickUtil.isFastClick) {
                    return@setOnClickListener
                }
                validateForm()


            }
            tvForgot.setOnClickListener {
                val intent = Intent(activity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun validateForm() {
        mAwesomeValidation.addValidation(
            binding.etConsumerNo, RegexTemplate.NOT_EMPTY,
            "Enter Valid Email"
        )
        mAwesomeValidation.addValidation(
            binding.etPassword,
            RegexTemplate.NOT_EMPTY,
            "Enter Valid Password"
        )
        callApi()

    }

    private fun callApi() {
        if (mAwesomeValidation.validate()) {
            val params: HashMap<String, Any> = geLoginBody()
            Commn.showProgress(activity)
            loginVM.loginApi(params)
        }
    }

    private fun geLoginBody(): HashMap<String, Any> {
        val params = HashMap<String, Any>()
        params["userName"] = binding.etConsumerNo.text.toString() + ""
        params["password"] = binding.etPassword.text.toString() + ""
        params["deviceType"] = Commn.deviceType
        params["deviceTokenID"] = deviceToken
        Commn.log("login_params:$params")
        return params
    }

    fun firebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    Log.d(TAG, "firebaseToken: Fetching FCM registration token failed" )
//                    Log.w(LoginActivity.TAG, "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                // Get new FCM registration token
                deviceToken = task.result.toString()
                Log.d(TAG, "firebaseToken: $deviceToken")
//                token?.let { Log.d(, it) }
            }
    }

}