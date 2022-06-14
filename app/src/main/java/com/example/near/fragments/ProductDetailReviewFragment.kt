package com.example.near.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.DetailReviewRecyclerAdapter
import com.example.near.databinding.FragmentProductDetailReviewBinding
import com.example.near.ui.product.ProductDetailPageActivity

class ProductDetailReviewFragment : BaseFragment() {
    lateinit var binding : FragmentProductDetailReviewBinding
    lateinit var mLeviewPageAdapter : DetailReviewRecyclerAdapter

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
        mLeviewPageAdapter = DetailReviewRecyclerAdapter(mContext, (mContext as ProductDetailPageActivity).mReviewsList)
        Log.d("(mContext as ProductDetailPageActivity).mReviewsList_____",(mContext as ProductDetailPageActivity).mReviewsList.toString())
        mLeviewPageAdapter.frag = this
        binding.detailReviewRecyclerView.adapter = mLeviewPageAdapter
        binding.detailReviewRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mLeviewPageAdapter.notifyDataSetChanged()
    }
}