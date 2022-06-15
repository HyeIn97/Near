package com.example.near.ui.Payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.EasyPaymentRecyclerAdapter
import com.example.near.databinding.ActivityEasyPaymentBinding
import com.example.near.models.BasicResponse
import com.example.near.models.CardData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EasyPaymentActivity : BaseActivity() {
    lateinit var binding: ActivityEasyPaymentBinding
    lateinit var mAdapter: EasyPaymentRecyclerAdapter
    val arrayCardList = ArrayList<CardData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_easy_payment)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.cardAddBtn.setOnClickListener {
            val myIntent = Intent(mContext, EasyPaymentCardAddActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        titleTxt.text = "간편 결제 관리"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        getData()
        initAdapter()
    }

    fun initAdapter() {
        mAdapter = EasyPaymentRecyclerAdapter(mContext, arrayCardList)
        binding.easyPaymentRecyclerView.adapter = mAdapter
        binding.easyPaymentRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getData() {
        apiList.getUserCardList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    arrayCardList.clear()
                    val br = response.body()!!
                    val cardList = br.data.cards
                    Log.d("cardList", cardList.toString())
                    if (cardList.size > 0) {
                        arrayCardList.addAll(cardList)
                        binding.easyPaymentRecyclerView.visibility = View.VISIBLE
                    }else{
                        binding.nonCardTxt.visibility = View.VISIBLE
                    }
                        Log.d("arrayCardList", arrayCardList.toString())
                        mAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}