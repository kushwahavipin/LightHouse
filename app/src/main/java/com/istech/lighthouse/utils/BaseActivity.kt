package com.istech.lighthouse.utils
import android.graphics.Color
import com.istech.lighthouse.utils.Commn.log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import java.util.HashMap

open class BaseActivity : AppCompatActivity() {
    public lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        sessionManager = SessionManager(applicationContext)
    }

    val header: HashMap<String, Any>
        get() {
            val params = HashMap<String, Any>()
            if (sessionManager.login) {
                params["Authorization"] = "Bearer " + sessionManager.user!!.data.token + ""
            }
            log("params:$params")
            return params
        }
}