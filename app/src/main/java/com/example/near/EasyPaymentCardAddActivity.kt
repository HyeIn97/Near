package com.example.near

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.near.databinding.ActivityEasyPaymentCardAddBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EasyPaymentCardAddActivity : BaseActivity() {
    lateinit var binding : ActivityEasyPaymentCardAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_easy_payment_card_add)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.addBtn.setOnClickListener {
            addCard()
        }
    }

    override fun setValues() {
        titleTxt.text = "카드 추가"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }

    fun addCard(){
        val inputCardNick = binding.cardNickNameEdt.text.toString()
        val inputCardNum = binding.cardNumEdt.text.toString()
        val inputCardPeriod = binding.cardPeriodEdt.text.toString()
        val inputBirth = binding.birthEdt.text.toString()
        val inputPw = binding.pwWordEdt.text.toString()

        apiList.postUserCardAdd(inputCardNum, inputCardNick, inputCardPeriod, inputBirth, inputPw).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
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
    }
}