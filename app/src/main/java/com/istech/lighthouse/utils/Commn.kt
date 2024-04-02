package com.istech.lighthouse.utils

import android.widget.Toast
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import java.lang.Exception

object Commn {
    val deviceType: String = "Android"
    fun log(tag: String) {
        Log.d("common_log", tag + "")
    }

    fun myToast(context: Context?, msg: String?) {
        if (context == null) {
            return
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    var progress: ProgressDialog? = null
    fun showProgress(mContext: Context?) {
        try {
            if (mContext != null) {
                if (progress != null && progress!!.isShowing) {
                    return
                }
                progress = ProgressDialog.show(mContext, "Please wait", null)
                if (progress != null) {
                    progress!!.setCancelable(true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgress() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
        }
    }
}