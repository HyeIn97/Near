package com.example.near.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.near.fragments.CartFragment
import com.example.near.fragments.CategoryFragment
import com.example.near.fragments.HomeFragment
import com.example.near.fragments.MyPageFragment

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