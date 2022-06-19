package com.example.near

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.near.adapters.SmallCategoryRecyclerAdapter
import com.example.near.databinding.ActivitySearchResultsBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import com.example.near.ui.product.ProductDetailPageActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsActivity : BaseActivity() {
    lateinit var binding : ActivitySearchResultsBinding
    lateinit var mSearchResultAdapter : SmallCategoryRecyclerAdapter
    var mProductList : ArrayList<ProductData> = arrayListOf()
    var word = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_results)
        word = intent.getStringExtra("word").toString()
        initAdapter()
        setUpEvents()
        setValues()

    }

    override fun onResume() {
        super.onResume()

    }
    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun setValues() {
        titleTxt.text = "검색결과"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        binding.searchWordTxt.text = word
        getData()

    }

    fun initAdapter(){
        mSearchResultAdapter = SmallCategoryRecyclerAdapter(mContext, mProductList)
        mSearchResultAdapter.setItemClickListener(object : SmallCategoryRecyclerAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {
                val myIntent = Intent(mContext, ProductDetailPageActivity::class.java)
                Log.d("mProductList[position]________", mProductList[position].toString())
                myIntent.putExtra("data", mProductList[position])
                startActivity(myIntent)
            }
        })
        binding.searchResultRecyclerView.adapter = mSearchResultAdapter
        binding.searchResultRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
    }

    fun getData(){
        apiList.getAllProductList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mProductList.clear()
                    val br = response.body()!!
                    val products = br.data.products
                    for(i in products){
                        if(i.name.contains(word)){
                            mProductList.add(i)
                        }
                    }
                    mSearchResultAdapter.notifyDataSetChanged()
                    if(mProductList.size > 0){
                        binding.searchResultLayout.visibility = View.VISIBLE
                    }else{
                        binding.nonSearchResultTxt.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}