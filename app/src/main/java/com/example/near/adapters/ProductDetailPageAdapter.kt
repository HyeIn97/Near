package com.example.near.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.near.fragments.ProductDetailInfoFragment
import com.example.near.fragments.ProductDetailReviewFragment

//상품 상세페이지 탭레이아웃 연결
class ProductDetailPageAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ProductDetailInfoFragment()
            else -> ProductDetailReviewFragment()
        }
    }
}