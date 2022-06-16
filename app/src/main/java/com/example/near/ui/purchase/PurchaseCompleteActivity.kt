package com.example.near.ui.purchase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.PurchaseRecyclerAdapter
import com.example.near.databinding.ActivityPurchaseCompleteBinding
import com.example.near.models.BasicResponse
import com.example.near.models.PaymentData
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class PurchaseCompleteActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseCompleteBinding
    lateinit var mPurshaseAdapter : PurchaseRecyclerAdapter
    var mSubscriptionList: ArrayList<PaymentData> = arrayListOf()
    lateinit var data : PaymentData
    var mProductList : ArrayList<ProductData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_complete)
        data = intent.getSerializableExtra("data") as PaymentData
        Log.d("datadatadatadatadatadatadata",data.toString())
        initAdapter()
        setUpEvents()
        setValues()
        getPurchase()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.toShopBtn.setOnClickListener {
            finish()
        }
        binding.moreInformationBtn.setOnClickListener {
            val myIntent = Intent(mContext, PurchaseListActivity::class.java)
            myIntent.putExtra("data", mSubscriptionList)
            startActivity(myIntent)
            finish()
        }
    }

    override fun setValues() {
        mProductList.add(data.subscription.product)
        titleTxt.text = "결제완료"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        val form = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.date)
        val sdr = SimpleDateFormat("yy-MM-dd")
        binding.purchaseDateTxt.text = sdr.format(form)
        binding.priceTxt.text = data.subscription.product.price.toString()
        binding.pointTxt.text = (data.amount.toInt()/100).toString()
    }

    fun initAdapter(){
        mPurshaseAdapter = PurchaseRecyclerAdapter(mContext, mProductList)
        binding.purchaseCompleteRecyclerView.adapter = mPurshaseAdapter
        binding.purchaseCompleteRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getPurchase() {
        apiList.getPaymentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mSubscriptionList.clear()
                    mSubscriptionList.addAll(br.data.payments)
                    Log.d("mSubscriptionList@@@@@@@@", mSubscriptionList.toString())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}