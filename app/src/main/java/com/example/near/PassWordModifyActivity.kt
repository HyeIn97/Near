package com.example.near

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.near.databinding.ActivityPassWordModifyBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassWordModifyActivity : BaseActivity() {
    val PASSWORD = "password"
    lateinit var binding: ActivityPassWordModifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pass_word_modify)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.modifyBtn.setOnClickListener {
            modifyPw()
        }
    }

    override fun setValues() {
        titleTxt.text = "비밀번호 변경"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }

    fun modifyPw() {
        val inputCurrentPw = binding.currentPwEdt.text.toString()
        val inputNewPw = binding.newPwEdt.text.toString()
        val inputNewCkPw = binding.newPwCkEdt.text.toString()
        if (inputNewPw == inputNewCkPw) {
            apiList.patchUserInfo(PASSWORD, inputNewPw, inputCurrentPw).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>,response: Response<BasicResponse>) {
                    if(response.isSuccessful){
                        val br = response.body()!!
                        Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        val errBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errBodyStr)
                        val message = jsonObj.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    }
                })
        }else{
            Toast.makeText(mContext, "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}