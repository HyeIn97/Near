package com.example.near.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.databinding.ActivityLoginBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.ui.MainActivity
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        var myIntent : Intent
        backBtn.setOnClickListener {
            finish()
        }
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()
            apiList.postRequestLogin(inputEmail, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val br = response.body()!!
                        ContextUtil.setLoginToken(mContext, br.data.token)
                        ContextUtil.setAutoLogin(mContext, binding.autoLoginCB.isChecked)
                        GlobalData.loginUser = br.data.user
                        Log.d("GlobalData.loginUser", GlobalData.loginUser.toString())
                        myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }else{
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })
        }
        binding.findIdBtn.setOnClickListener {
            myIntent = Intent(mContext, FindIdActivity::class.java)
            startActivity(myIntent)
        }
        binding.findPwBtn.setOnClickListener {
            myIntent = Intent(mContext, FindPwActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        titleTxt.text = "로그인"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }
}