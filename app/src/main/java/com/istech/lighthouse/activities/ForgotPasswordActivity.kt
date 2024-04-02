package com.istech.lighthouse.activities

import android.content.Context
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ForgotPasswordActivityBinding
import com.istech.lighthouse.utils.BaseActivity
import com.istech.lighthouse.utils.Commn
import com.istech.lighthouse.utils.FastClickUtil
import com.istech.lighthouse.viewmodel.LoginVM
import java.util.HashMap

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ForgotPasswordActivityBinding
    private lateinit var mAwesomeValidation: AwesomeValidation
    private lateinit var activity: ForgotPasswordActivity
    private lateinit var context: Context
    private lateinit var loginVM: LoginVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.forgot_password_activity)
        activity = this
        context = activity
        initView()
        handleClick()
        initObserver()
    }
    private fun initObserver() {
        loginVM.forgotPasswordResponse.observe(this) { loginModel ->
            if (loginModel != null) {
                Commn.hideProgress()
                Commn.myToast(context, loginModel.message + "")
                if (loginModel.success) {
                    onBackPressed()
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
            btSubmit.setOnClickListener {
                if (FastClickUtil.isFastClick) {
                    return@setOnClickListener
                }
                validateForm()

            }
            ivBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun validateForm() {
        mAwesomeValidation.addValidation(
            binding.etConsumerNo, RegexTemplate.NOT_EMPTY,
            "Enter Your Email"
        )

        callApi()

    }

    private fun callApi() {
        if (mAwesomeValidation.validate()) {
            val params: HashMap<String, Any> = getBody()
            Commn.showProgress(activity)
            loginVM.forgotPasswordApi(params)
        }
    }

    private fun getBody(): HashMap<String, Any> {
        val params = HashMap<String, Any>()
        params["emailaddress"] = binding.etConsumerNo.text.toString() + ""

        Commn.log("getBody:$params")
        return params

    }
}