package com.example.near

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.adapters.PurchaseRecyclerAdapter
import com.example.near.databinding.ActivityPurchaseCompleteBinding
import com.example.near.models.PaymentData
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity

class PurchaseCompleteActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseCompleteBinding
    lateinit var mPurshaseAdapter : PurchaseRecyclerAdapter
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
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.toShopBtn.setOnClickListener {
            finish()
        }
        binding.moreInformationBtn.setOnClickListener {
            val myIntent = Intent(mContext, PurchaseCompleteActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    override fun setValues() {
        mProductList.add(data.subscription.product)
        titleTxt.text = "결제완료"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        binding.purchaseDateTxt.text = data.date
        binding.priceTxt.text = data.subscription.product.price.toString()
        binding.pointTxt.text = (data.amount.toInt()/100).toString()
    }

    fun initAdapter(){
        mPurshaseAdapter = PurchaseRecyclerAdapter(mContext, mProductList)
        binding.purchaseCompleteRecyclerView.adapter = mPurshaseAdapter
        binding.purchaseCompleteRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }
}