package com.istech.lighthouse.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.istech.lighthouse.R
import com.istech.lighthouse.databinding.ActivityContactUsBinding


class ContactUsActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityContactUsBinding
    private lateinit var call_ll: LinearLayout
    private lateinit var mail_ll: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        bindView()
        initView()
    }

    private fun bindView() {
        call_ll = binding.callLl
        mail_ll = binding.mailLl
        call_ll.setOnClickListener(this)
        mail_ll.setOnClickListener(this)
    }

    private fun initView() {
        binding.rlHeader.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            tvTitle.text = "Help & Support"
        }

    }

    override fun onClick(view: View?) {
        if (view == call_ll){
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:9810737751")
            startActivity(intent)
        } else if (view == mail_ll){
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.lighthouse_gmail_com)))
            email.putExtra(Intent.EXTRA_SUBJECT, "")
            email.putExtra(Intent.EXTRA_TEXT, "")
            email.type = "message/rfc822"
            startActivity(Intent.createChooser(email, "Complete action using"))
        } 
    }
}