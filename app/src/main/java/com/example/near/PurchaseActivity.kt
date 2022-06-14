package com.example.near

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.adapters.PurchaseRecyclerAdapter
import com.example.near.databinding.ActivityPurchaseBinding
import com.example.near.models.BasicResponse
import com.example.near.models.CardData
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseBinding
    lateinit var data : ProductData
    lateinit var purchaseAdapter : PurchaseRecyclerAdapter
    lateinit var spinnerAdapter : ArrayAdapter<String>
    var mCardList : ArrayList<CardData> = arrayListOf()
    var mCardNickList : ArrayList<String> = arrayListOf()
    var mList : ArrayList<ProductData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        data = intent.getSerializableExtra("data") as ProductData
        mList.add(data)
        Log.d("mListmListmListmListmListmListmList_____", mList.toString())
        Log.d("PurchaseActivitydata_____", data.toString())
        getCardList()
        initAdapter()
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.buyOkBtn.setOnClickListener {
            purchaseProduct()
        }
//        spinnerAdapter = ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, mCardList)
//        binding.checkCardSpinner.adapter = spinnerAdapter
        binding.checkCardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Toast.makeText(mContext, mCardList[position].toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun setValues() {
        titleTxt.text = "결제"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        cartBtn.visibility = View.VISIBLE
        homeBtn.visibility = View.VISIBLE

        spinner()


    }

    fun initAdapter(){
        purchaseAdapter = PurchaseRecyclerAdapter(mContext, mList)
        binding.purchaseRecyclerView.adapter = purchaseAdapter
        binding.purchaseRecyclerView.layoutManager = LinearLayoutManager(mContext)
        purchaseAdapter.notifyDataSetChanged()
    }

    fun purchaseProduct(){

    }

    fun getCardList(){



        apiList.getUserCardList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){
                    val br = response.body()!!
                    mCardList.addAll(br.data.cards)
                    Log.d("mCardList______", mCardList.toString())
                    mCardNickList.addAll(br.data.cards.map { it.cardNick })
                    Log.d("mCardNickList______", mCardNickList.toString())
                    spinnerAdapter = ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, mCardNickList) //킵더 타임 출발지 설명 보고 따라하기
                    binding.checkCardSpinner.adapter = spinnerAdapter
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }
    fun spinner(){
//        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mCardList)
//        binding.checkCardSpinner.adapter = adapter
    }
}