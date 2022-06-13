package com.example.near.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.near.fragments.*

//메인페이지 바텀네비게이션바 어댑터
class MainViewPagerAtapter(fm : FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> CategoryFragment()
            2 -> CartFragment()
            else -> MyPageFragment()
        }
    }
}