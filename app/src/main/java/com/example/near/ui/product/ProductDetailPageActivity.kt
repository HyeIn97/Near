package com.example.near.ui.product

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.adapters.ProductDetailPageAdapter
import com.example.near.databinding.ActivityProductDetailPageBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.models.ReviewData
import com.example.near.models.StoreData
import com.example.near.ui.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailPageActivity : BaseActivity() {
    lateinit var binding : ActivityProductDetailPageBinding
    lateinit var mProductDetailPageAdapter : ProductDetailPageAdapter
    lateinit var data : ProductData
    lateinit var mStoreObj : StoreData
    lateinit var mReviewsList : ArrayList<ReviewData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail_page)
        data = intent.getSerializableExtra("data") as ProductData
        Log.d("data________!!!", data.toString())
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun setValues() {
        backBtn.visibility = View.VISIBLE
        cartBtn.visibility = View.VISIBLE
        homeBtn.visibility = View.VISIBLE
        getData()
        tabLayout()
    }

    fun getData(){
        apiList.getProductDetail(data.id.toString()).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    mStoreObj = br.data.product.store!!
                    mReviewsList = br.data.product.reviews!!
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun tabLayout(){
        mProductDetailPageAdapter = ProductDetailPageAdapter(this)
        binding.detailPageViewPager.adapter = mProductDetailPageAdapter

        TabLayoutMediator(binding.detailPageTabLayout, binding.detailPageViewPager){tab, positon ->
            when(positon){
                0 -> tab.text = "상세보기"
                else -> tab.text = "리뷰"
            }
        }.attach()
    }
}