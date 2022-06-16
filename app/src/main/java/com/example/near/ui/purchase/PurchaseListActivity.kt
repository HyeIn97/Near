package com.example.near.ui.purchase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.ui.review.WriteReviewActivity
import com.example.near.adapters.PurchaseListRecyclerAdapter
import com.example.near.databinding.ActivityPurchaseListBinding
import com.example.near.models.PaymentData
import com.example.near.ui.BaseActivity
import com.example.near.ui.product.ProductDetailPageActivity

class PurchaseListActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseListBinding
    lateinit var mPurchaseListAdapter : PurchaseListRecyclerAdapter
    var data : ArrayList<PaymentData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_list)
        data = intent.getSerializableExtra("data") as ArrayList<PaymentData>
        Log.d("data@@@@@@@@", data.toString())
        initAdapter()
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }

    }

    override fun setValues() {
        titleTxt.text = "구독 목록"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }

    fun initAdapter(){
        mPurchaseListAdapter = PurchaseListRecyclerAdapter(mContext, data)

        mPurchaseListAdapter.setItemClickListener(object : PurchaseListRecyclerAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {

                val myIntent = Intent(mContext, ProductDetailPageActivity::class.java)
                myIntent.putExtra("data", data[position].subscription.product)
                startActivity(myIntent)
//                val myIntent = Intent(mContext, WriteReviewActivity::class.java)
//                myIntent.putExtra("data", data[position].subscription.product)
//                startActivity(myIntent)
            }
        })

        binding.purchaseListRecyclerView.adapter = mPurchaseListAdapter
        binding.purchaseListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }
}