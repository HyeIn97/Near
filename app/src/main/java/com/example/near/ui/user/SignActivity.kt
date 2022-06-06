package com.example.near.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.databinding.ActivitySignBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignActivity : BaseActivity() {
    lateinit var binding: ActivitySignBinding
    var checkEmail = false
    var checkNickName = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.inputPwEdt.addTextChangedListener {
            pwCheck()
        }
        binding.pwCheckEdt.addTextChangedListener {
            pwCheck()
        }
        binding.inputEmailEdt.addTextChangedListener {
            checkEmail = false
            binding.checkEmailTxt.text = "아이디 중복 검사를 진행해주세요."
        }
        binding.checkEmailBtn.setOnClickListener {
            val inputEmail = binding.inputEmailEdt.text.toString()
            apiList.getRequestCheck("EMAIL", inputEmail).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Log.d("이메일 중복확인", br.message)
                        if (br.code == 200) {
                            checkEmail = true
                            binding.checkEmailTxt.text = br.message
                        }
                    } else {
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        val code = jsonObj.getInt("code")
                        if (code == 400) {
                            binding.checkEmailTxt.text = jsonObj.getString("message")
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })

        }
        binding.inputNickEdt.addTextChangedListener {
            checkNickName = false
            binding.checkNickTxt.text = "닉네임 중복 검사를 진행해주세요."
        }
        binding.checkNickBtn.setOnClickListener {
            val inputNick = binding.inputNickEdt.text.toString()
            apiList.getRequestCheck("NICK_NAME", inputNick)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!
                            Log.d("닉네임 중복확인", br.message)
                            if (br.code == 200) {
                                checkNickName = true
                                binding.checkNickTxt.text = br.message
                            }
                        } else {
                            val errorBodyStr = response.errorBody()!!.string()
                            val jsonObj = JSONObject(errorBodyStr)
                            val code = jsonObj.getInt("code")
                            if (code == 400) {
                                binding.checkNickTxt.text = jsonObj.getString("message")
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    }
                })

        }
        binding.signBtn.setOnClickListener {
            val inputEmail = binding.inputEmailEdt.text.toString()
            val inputPw = binding.inputPwEdt.text.toString()
            val inputCkPw = binding.pwCheckEdt.text.toString()
            val inputNickName = binding.inputNickEdt.text.toString()
            val inputPhone = binding.inputPhoneEdt.text.toString()
            if (inputPw == inputCkPw) {
                binding.checkPwTxt.text = "비밀번호 일치"
                if (checkEmail && checkNickName) {
                    apiList.putRequestSign(inputEmail, inputPw, inputNickName, inputPhone)
                        .enqueue(object : Callback<BasicResponse> {
                            override fun onResponse(
                                call: Call<BasicResponse>,
                                response: Response<BasicResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(mContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    val myIntent = Intent(mContext, LoginActivity::class.java)
                                    startActivity(myIntent)
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                            }
                        })
                } else {
                    Toast.makeText(mContext, "중복 검사가 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(mContext, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setValues() {
        backBtn.visibility = View.VISIBLE
        titleTxt.text = "회원가입"
        titleTxt.visibility = View.VISIBLE
    }

    fun pwCheck(){
        val inputPw = binding.inputPwEdt.text.toString()
        val inputCkPw = binding.pwCheckEdt.text.toString()

        if(inputPw != inputCkPw){
            binding.checkPwTxt.text = "비밀번호가 불일치 합니다."
            binding.checkPwTxt.visibility = View.VISIBLE
        }else{
            binding.checkPwTxt.visibility = View.GONE
        }
    }
}