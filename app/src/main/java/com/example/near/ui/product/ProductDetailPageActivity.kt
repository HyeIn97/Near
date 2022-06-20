package com.example.near.ui.product

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.MyReceiver
import com.example.near.ui.purchase.PurchaseActivity
import com.example.near.R
import com.example.near.adapters.ProductDetailPageAdapter
import com.example.near.databinding.ActivityProductDetailPageBinding
import com.example.near.fragments.BottomSheetDialogFragment
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
    var mReviewsList : ArrayList<ReviewData> = arrayListOf()
    lateinit var mReceiveer : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail_page)
        data = intent.getSerializableExtra("data") as ProductData
        Log.d("data________!!!", data.toString())
        mReceiveer = MyReceiver()
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(MyReceiver.MainAction)
        filter.addAction(MyReceiver.CartAction)
        registerReceiver(mReceiveer, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiveer)
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.buyBtn.setOnClickListener {
            val orderBottomDialogFragment : BottomSheetDialogFragment = BottomSheetDialogFragment{
                when(it){
                    0 -> {
                        cart()
                    }
                    1 -> {
                        val myIntent = Intent(mContext, PurchaseActivity::class.java)
                        myIntent.putExtra("data", data)
                        startActivity(myIntent)
                    }
                }
            }
            orderBottomDialogFragment.show(supportFragmentManager, orderBottomDialogFragment.tag)
        }
        homeBtn.setOnClickListener {
            val myIntent = Intent(MyReceiver.MainAction)
            sendBroadcast(myIntent)
            overridePendingTransition(0, 0)
            ActivityCompat.finishAffinity(this)
            overridePendingTransition(0, 0)
        }
        cartBtn.setOnClickListener {
            val myIntent = Intent(MyReceiver.CartAction)
            sendBroadcast(myIntent)
            overridePendingTransition(0, 0)
            ActivityCompat.finishAffinity(this)
            overridePendingTransition(0, 0)
        }
    }

    override fun setValues() {
        backBtn.visibility = View.VISIBLE
        cartBtn.visibility = View.VISIBLE
        homeBtn.visibility = View.VISIBLE
        tabLayout()
        getData()
    }

    fun getData(){
        apiList.getProductDetail(data.id.toString()).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    mStoreObj = br.data.product.store!!
                    mReviewsList = br.data.product.reviews!!
                    Log.d("mReviewsList________!!!!!!",mReviewsList.toString())
                    //mLeviewPageAdapter.notifyDataSetChanged()
                    setData()
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

    fun setData(){
        Glide.with(mContext).load(data.img).into(binding.productImg)
        binding.productNameTxt.text = data.name
        binding.productPriceTxt.text = data.price.toString() + " 원"
        binding.storeNameTxt.text = mStoreObj.name
        //Glide.with(mContext).load(mStoreObj.img).into(binding.storeProfileImg)
    }

    fun cart(){
        apiList.postAddCart(data.id.toString()).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    Toast.makeText(mContext, "장바구니에 담았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

//    fun initAdapter(){
//        mLeviewPageAdapter = DetailReviewRecyclerAdapter(mContext, mReviewsList)
//        reviewTapBinding.detailReviewRecyclerView.adapter = mLeviewPageAdapter
//        reviewTapBinding.detailReviewRecyclerView.layoutManager = LinearLayoutManager(mContext)
//    }
}