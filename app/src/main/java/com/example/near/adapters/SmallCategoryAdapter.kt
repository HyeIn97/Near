package com.example.near.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.near.fragments.SmallCategoryFragment
import com.example.near.models.SmallCategoryData

class SmallCategoryAdapter(fa : FragmentActivity, val smallList : ArrayList<SmallCategoryData>) : FragmentStateAdapter(fa) {


    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SmallCategoryFragment(smallList[0].id)
            1 -> SmallCategoryFragment(smallList[1].id)
            2 -> SmallCategoryFragment(smallList[2].id)
            3 -> SmallCategoryFragment(smallList[3].id)
            else -> SmallCategoryFragment(smallList[4].id)
        }
    }

}