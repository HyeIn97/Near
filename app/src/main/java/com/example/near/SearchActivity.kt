package com.example.near

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.adapters.SearchLatelyRecyclerAdapter
import com.example.near.adapters.SearchRakingRecyclerAdapter
import com.example.near.databinding.ActivitySearchBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : BaseActivity() {
    lateinit var binding : ActivitySearchBinding
    lateinit var mSearchRakingAdapter : SearchRakingRecyclerAdapter
    lateinit var mSearchLatelyAdapter : SearchLatelyRecyclerAdapter
    var mSearchWordList : ArrayList<String> = arrayListOf()
    var mProdcutList : ArrayList<ProductData> = arrayListOf()
    var inputSearchTxt = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initRakingAdapter()
        initSearchAdapter()
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        binding.searchDelBtn.setOnClickListener {
            database.execSQL("DELETE FROM mytable")
            mSearchWordList.clear()
            if(mSearchWordList.size > 0){
                binding.latelySearchRecyclerView.visibility = View.VISIBLE
                binding.nonLatelySearchTxt.visibility = View.GONE
            }else{
                binding.nonLatelySearchTxt.visibility = View.VISIBLE
                binding.latelySearchRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.searchBtn.setOnClickListener {
            inputSearchTxt = binding.inputSearchEdt.text.toString()
            if(inputSearchTxt != ""){
                binding.latelySearchRecyclerView.visibility = View.VISIBLE
                binding.nonLatelySearchTxt.visibility = View.GONE
                insertDB()
                selectDB()
                val myIntent = Intent(mContext, SearchResultsActivity::class.java)
                myIntent.putExtra("word", inputSearchTxt)
                startActivity(myIntent)
            }else{
                Toast.makeText(mContext, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun setValues() {
        titleTxt.text = "검색"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        getAllReview()
        selectDB()
        if(mSearchWordList.size > 0){
            binding.latelySearchRecyclerView.visibility = View.VISIBLE
        }else{
            binding.nonLatelySearchTxt.visibility = View.VISIBLE
        }
    }

    fun getAllReview(){
        apiList.getAllReview().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mProdcutList.clear()
                    val br = response.body()!!
                    val reviews = br.data.reviews
//                    for(i in reviews){
//                                if(mProdcutList.size < 10) {
//                                    mProdcutList.add(i.product)
//                        }
//                   }

                    for(i in reviews){
                    while (true){
                            if(!mProdcutList.contains(i.product)){
                                if(mProdcutList.size < 10){
                                    mProdcutList.add(i.product)
                                    break
                                }
                                Log.d("mProdcutListmProdcutListmProdcutList", mProdcutList.toString())
                            }
                        }
                    }
                    mSearchRakingAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun initRakingAdapter(){
        mSearchRakingAdapter = SearchRakingRecyclerAdapter(mContext, mProdcutList)
        binding.popSearchRecyclerView.adapter = mSearchRakingAdapter
        binding.popSearchRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun initSearchAdapter(){
        mSearchLatelyAdapter = SearchLatelyRecyclerAdapter(mContext, mSearchWordList)
        binding.latelySearchRecyclerView.adapter = mSearchLatelyAdapter
        binding.latelySearchRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    }

    fun insertDB(){
        inputSearchTxt = binding.inputSearchEdt.text.toString()
        var query = "INSERT INTO mytable('search') values('${inputSearchTxt}');"
        database.execSQL(query)
    }

    @SuppressLint("Range")
    fun selectDB(){
        var query = "SELECT * FROM mytable"
        val cursor = database.rawQuery(query, null)
        mSearchWordList.clear()
        while (cursor.moveToNext()){
            var searchWord = cursor.getString(cursor.getColumnIndex("search"))
            mSearchWordList.add(searchWord)
        }
        mSearchWordList.reverse()
        mSearchLatelyAdapter.notifyDataSetChanged()
    }
}