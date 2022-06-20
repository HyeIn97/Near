package com.example.near.ui.purchase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.PurchaseRecyclerAdapter
import com.example.near.databinding.ActivityPurchaseBinding
import com.example.near.models.BasicResponse
import com.example.near.models.CardData
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseBinding
    lateinit var data : ProductData
    lateinit var purchaseAdapter : PurchaseRecyclerAdapter
    lateinit var spinnerAdapter : ArrayAdapter<String>
    lateinit var product : ProductData
    var mCardList : ArrayList<CardData> = arrayListOf()
    var mCardNickList : ArrayList<String> = arrayListOf()
    var mList : ArrayList<ProductData> = arrayListOf()
    var mCheckCard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        data = intent.getSerializableExtra("data") as ProductData
        mList.add(data)
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
        binding.checkCardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                mCheckCard = mCardList[position].id.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun setValues() {
        titleTxt.text = "결제"
        titleTxt.visibility = View.VISIBLE
    }

    fun initAdapter(){
        purchaseAdapter = PurchaseRecyclerAdapter(mContext, mList)
        binding.purchaseRecyclerView.adapter = purchaseAdapter
        binding.purchaseRecyclerView.layoutManager = LinearLayoutManager(mContext)
        purchaseAdapter.notifyDataSetChanged()
    }

    fun purchaseProduct(){
        apiList.postPurchaseProduct(data.id.toString(), mCheckCard).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    val product = br.data.payment
                    val myIntent = Intent(mContext, PurchaseCompleteActivity::class.java)
                    myIntent.putExtra("data", product)
                    startActivity(myIntent)
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

    fun getCardList(){
        apiList.getUserCardList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){
                    val br = response.body()!!
                    mCardList.addAll(br.data.cards)
                    mCardNickList.addAll(br.data.cards.map { it.cardNick })
                    spinnerAdapter = ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, mCardNickList) //킵더 타임 출발지 설명 보고 따라하기
                    binding.checkCardSpinner.adapter = spinnerAdapter
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })

    }
}