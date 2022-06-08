package com.example.near.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.near.R
import com.example.near.databinding.ActivityUserInfoBinding
import com.example.near.ui.BaseActivity

class UserInfoActivity : BaseActivity() {
    lateinit var binding : ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
    }

    override fun setValues() {
        titleTxt.text = "회원정보"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }
}