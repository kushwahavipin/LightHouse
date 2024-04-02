package com.istech.lighthouse.utils

import android.util.Log

/**
 * @author zsy_18 data:2018/9/29
 */
object FastClickUtil {
    const val MIN_DELAY_TIME = 500 // 两次点击间隔不能少于500ms
    private var lastClickTime: Long = 0
    const val MIN_ADS_DELAY_TIME = 20000
    private var lastNoRecordClickTime: Long = 0
    private var sLastClickTime: Long = 0
    val isFastClick: Boolean
        get() {
            var flag = true
            val currentClickTime = System.currentTimeMillis()
            if (currentClickTime - lastClickTime >= MIN_DELAY_TIME) {
                flag = false
            }
            lastClickTime = currentClickTime
            lastNoRecordClickTime = currentClickTime
            return flag
        }
    val isFastLoading: Boolean
        get() {
            val currentClickTime = System.currentTimeMillis()
            val isFastClick = currentClickTime - sLastClickTime <= MIN_ADS_DELAY_TIME
            Log.e("TAG", "log_common_FastClickUtil : " + (currentClickTime - sLastClickTime))
            sLastClickTime = currentClickTime
            return isFastClick
        }

    /**
     * 用于判断录制按钮和其他按钮同时按下
     * @return
     */
    val isRecordWithOtherClick: Boolean
        get() {
            var flag = true
            val currentClickTime = System.currentTimeMillis()
            if (currentClickTime - lastNoRecordClickTime >= MIN_DELAY_TIME) {
                flag = false
            }
            lastClickTime = currentClickTime
            return false
        }
}