package com.example.near.ui.review

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.UserReviewListRecyclerAdapter
import com.example.near.databinding.ActivityUserReviewListBinding
import com.example.near.models.ReviewData
import com.example.near.ui.BaseActivity

class UserReviewListActivity : BaseActivity() {
    lateinit var binding : ActivityUserReviewListBinding
    lateinit var mReviewListAdapter : UserReviewListRecyclerAdapter
    var data : ArrayList<ReviewData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_review_list)
        data = intent.getSerializableExtra("data") as ArrayList<ReviewData>
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
        titleTxt.text = "리뷰 목록"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
    }

    fun initAdapter(){
        mReviewListAdapter = UserReviewListRecyclerAdapter(mContext, data)
        mReviewListAdapter.setItemClickListener(object : UserReviewListRecyclerAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {
                val myIntent = Intent(mContext, ReviewDetailPageActivity::class.java)
                myIntent.putExtra("data", data[position])
                startActivity(myIntent)
            }
        })
        binding.userReviewListRecyclerView.adapter = mReviewListAdapter
        binding.userReviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mReviewListAdapter.notifyDataSetChanged()
    }
}