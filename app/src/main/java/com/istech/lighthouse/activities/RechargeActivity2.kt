package com.istech.lighthouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import com.istech.lighthouse.R

class RechargeActivity2 : AppCompatActivity() {

    private lateinit var pweActivityResultLauncher :ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge2)
        lateinit var btnRecharge:Button
        btnRecharge.setOnClickListener{}
    }
}