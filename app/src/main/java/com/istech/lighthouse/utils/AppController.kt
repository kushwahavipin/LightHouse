package com.istech.lighthouse.utils

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.multidex.MultiDex

class AppController : MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}