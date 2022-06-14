package com.example.near

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.near.adapters.ReviewDetailPageRecyclerAdapter
import com.example.near.databinding.ActivityReviewDetailPageBinding
import com.example.near.models.BasicResponse
import com.example.near.models.RepliesData
import com.example.near.models.ReviewData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDetailPageActivity : BaseActivity() {
    lateinit var binding : ActivityReviewDetailPageBinding
    lateinit var data : ReviewData
    lateinit var mReviewRepliesAdapter : ReviewDetailPageRecyclerAdapter
    var mRepliesList : ArrayList<RepliesData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail_page)
        data = intent.getSerializableExtra("data") as ReviewData
        Log.d("리뷰 datadata",data.toString())
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
        reviewPageActionBarLayout.visibility = View.VISIBLE
        Glide.with(mContext).load(data.user.profileImg).into(userImg)
        nickNameTxt.text = data.user.nickName
        backBtn.visibility = View.VISIBLE
        setApiData()
        addValue()
    }

    fun setApiData(){
        apiList.getReviewReply(data.id.toString()).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    mRepliesList.addAll(br.data.replies)
                }
                mReviewRepliesAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun initAdapter(){
        mReviewRepliesAdapter = ReviewDetailPageRecyclerAdapter(mContext, mRepliesList)
        binding.reviewDetailPageRecyclerView.adapter = mReviewRepliesAdapter
        binding.reviewDetailPageRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun addValue(){
        binding.titleTxt.text = data.title
        binding.contentTxt.text = data.content
        //binding.dateTxt.text = data.
    }
}