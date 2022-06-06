package com.example.near.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.databinding.ActivityFindIdBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdActivity : BaseActivity() {
    lateinit var binding : ActivityFindIdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_id)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        binding.findIdBtn.setOnClickListener {
            val inputNickName = binding.nickNameEdt.text.toString()
            val inputPhone = binding.phoneEdt.text.toString()
            apiList.getRequestFindId(inputNickName, inputPhone).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful){
                        val br = response.body()!!
                        Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                        Log.d("message : ", br.message)
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
        binding.findPwBtn.setOnClickListener {
            val myIntent = Intent(mContext, FindPwActivity::class.java)
            startActivity(myIntent)
            finish()
        }
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun setValues() {
        titleTxt.text = "아이디 찾기"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }
}