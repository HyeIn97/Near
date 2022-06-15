package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.ui.review.ReviewDetailPageActivity
import com.example.near.adapters.ReviewListRecyclerAdapter
import com.example.near.databinding.FragmentProductDetailReviewBinding
import com.example.near.ui.product.ProductDetailPageActivity

class ProductDetailReviewFragment : BaseFragment() {
    lateinit var binding : FragmentProductDetailReviewBinding
    lateinit var mLeviewPageAdapter : ReviewListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail_review, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        //(mContext as ProductDetailPageActivity).mReviewsList
    }

    override fun setValues() {
        initAdapter()
    }

    fun initAdapter(){
        mLeviewPageAdapter = ReviewListRecyclerAdapter(mContext, (mContext as ProductDetailPageActivity).mReviewsList)
        mLeviewPageAdapter.frag = this
        mLeviewPageAdapter.setItemClickListener(object : ReviewListRecyclerAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {
                val myIntent = Intent(mContext, ReviewDetailPageActivity::class.java)
                myIntent.putExtra("data", (mContext as ProductDetailPageActivity).mReviewsList[position])
                startActivity(myIntent)
            }
        })
        binding.detailReviewRecyclerView.adapter = mLeviewPageAdapter
        binding.detailReviewRecyclerView.layoutManager = LinearLayoutManager(mContext)
        if((mContext as ProductDetailPageActivity).mReviewsList.size > 0){
            binding.detailReviewRecyclerView.visibility = View.VISIBLE
        }else{
            binding.nonReview.visibility = View.VISIBLE
        }
        Log.d("(mContext as ProductDetailPageActivity).mReviewsList.size", (mContext as ProductDetailPageActivity).mReviewsList.size.toString())
        mLeviewPageAdapter.notifyDataSetChanged()
    }
}