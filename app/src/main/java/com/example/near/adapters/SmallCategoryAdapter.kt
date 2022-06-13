package com.example.near.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.near.fragments.SmallCategoryFragment
import com.example.near.models.SmallCategoryData

//소분류 탭레이아웃 연결
class SmallCategoryAdapter(fa : FragmentActivity, val smallList : ArrayList<SmallCategoryData>) : FragmentStateAdapter(fa) {


    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
         return SmallCategoryFragment(smallList[position].id)
    }

}