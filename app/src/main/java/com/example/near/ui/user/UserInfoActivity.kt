package com.example.near.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.databinding.ActivityUserInfoBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.utils.GlobalData
import retrofit2.Callback

class UserInfoActivity : BaseActivity() {
    lateinit var binding : ActivityUserInfoBinding
    var loginUser = GlobalData.loginUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        loginUser = GlobalData.loginUser!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        binding.modifyBtn.setOnClickListener {
            val myItnent = Intent(mContext, ModifyActivity::class.java)
            startActivity(myItnent)
        }
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun setValues() {
        titleTxt.text = "회원정보"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        getUserInfo()
    }
    fun getUserInfo(){
        Glide.with(mContext).load(loginUser.profileImg).into(binding.userImg)
        binding.userNickTxt.text = loginUser.nickName
        binding.userEmail.text = loginUser.email
        binding.userPhoneTxt.text = loginUser.phone
    }
}