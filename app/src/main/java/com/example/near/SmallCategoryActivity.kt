package com.example.near

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.near.adapters.SmallCategoryAdapter
import com.example.near.databinding.ActivitySmallCategoryBinding
import com.example.near.fragments.CartFragment
import com.example.near.models.LageCategoryData
import com.example.near.ui.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class SmallCategoryActivity : BaseActivity() {
    lateinit var binding : ActivitySmallCategoryBinding
    lateinit var myIntent : Intent
    lateinit var mSmallCategoryAdapter : SmallCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_small_category)
        setUpEvents()
        setValues()

    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        homeBtn.setOnClickListener {
            //프래그먼트 이동으로 해야됨
        }
        cartBtn.setOnClickListener {
            myIntent = Intent(mContext, CartFragment::class.java)
            //프래그먼트 이동으로 해야됨
        }



    }

    override fun setValues() {
        val list = intent.getSerializableExtra("list") as LageCategoryData?
        titleTxt.text = list!!.name
        Log.d("mediaContentList_______", list.toString())
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        homeBtn.visibility = View.VISIBLE
        cartBtn.visibility = View.VISIBLE

        val smallList = list.smallCategory
        mSmallCategoryAdapter = SmallCategoryAdapter(this, smallList)
        Log.d("id______", list.id.toString())
        binding.smallCategoryViewPager.adapter = mSmallCategoryAdapter

        Log.d("list.smallCategory_______", list.smallCategory.toString())


        TabLayoutMediator(binding.tabLayout, binding.smallCategoryViewPager) { tab, positon ->
//            for(i in smallList){
//                tab.text = i.name
//                Log.d("tab.text_______", tab.text.toString())
//            }
            when(positon){
                0 -> tab.text = smallList[0].name
                1 -> tab.text = smallList[1].name
                2 -> tab.text = smallList[2].name
                3 -> tab.text = smallList[3].name
                else -> tab.text = smallList[4].name
            }
        }.attach()
    }
}