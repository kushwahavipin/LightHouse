package com.istech.lighthouse.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.istech.lighthouse.R
import com.istech.lighthouse.utils.BaseActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(mainLooper)
            .postDelayed(Runnable {
                if (sessionManager.login.equals(true)) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }

            }, 2000)
    }
}