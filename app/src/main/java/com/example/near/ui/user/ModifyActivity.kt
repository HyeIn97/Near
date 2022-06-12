package com.example.near.ui.user

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.databinding.ActivityModifyBinding
import com.example.near.fragments.MyPageFragment
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.ui.MainActivity
import com.example.near.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyActivity : BaseActivity() {
    lateinit var binding : ActivityModifyBinding
    val USERNICK = "nickname"
    val USERPHONE = "phone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify)
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.modifyBtn.setOnClickListener {
            finish()
        }
        binding.nickModifyBtn.setOnClickListener {
            modifyUserNick()
        }
        binding.phoneModifyBtn.setOnClickListener {
            modifyUserPhone()
        }
    }

    override fun setValues() {
        titleTxt.text = "회원정보 수정"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE

        Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(binding.userImg)
        binding.userEmail.text = GlobalData.loginUser!!.email
        binding.userNickEdt.hint = GlobalData.loginUser!!.nickName
        binding.userPhoneEdt.hint = GlobalData.loginUser!!.phone
    }

    fun modifyUserNick(){
        val userNick = binding.userNickEdt.text.toString()
        apiList.patchUserInfo(USERNICK, userNick, null).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                }else{
                    val errorBodyStr = response.errorBody()!!.string()
                    Log.d("errorBodyStr", errorBodyStr)
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun modifyUserPhone(){
        val userPhone = binding.userPhoneEdt.text.toString()
        apiList.patchUserInfo(USERPHONE, userPhone, null).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                }else{
                    val errBody = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errBody)
                    val message =  jsonObj.getString("message")
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
    /*fun refresh(){
        val myIntent = Intent(mContext, MainActivity::class.java)
        myIntent.putExtra("")
    }*/


}