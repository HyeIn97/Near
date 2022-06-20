package com.example.near.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.adapters.SmallCategoryAdapter
import com.example.near.databinding.ActivitySmallCategoryBinding
import com.example.near.fragments.BaseFragment
import com.example.near.fragments.CartFragment
import com.example.near.fragments.HomeFragment
import com.example.near.models.BasicResponse
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmallCategoryActivity : BaseActivity() {
    lateinit var binding : ActivitySmallCategoryBinding
    lateinit var myIntent : Intent
    lateinit var mSmallCategoryAdapter : SmallCategoryAdapter
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_small_category)
        position = intent.getIntExtra("position", 0)
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayout.getTabAt(position)!!.select()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        homeBtn.setOnClickListener {
            finish()

            //여기서 메인을 호출,,?
            //setFragment("home")
            Log.d("타긴하는거?", "웅애 소분류")
        }
        cartBtn.setOnClickListener {
            myIntent = Intent(mContext, CartFragment::class.java)
            //프래그먼트 이동으로 해야됨
        }



    }

    override fun setValues() {
        val lageTitle = intent.getSerializableExtra("lageData") as LageCategoryData
        val data = intent.getSerializableExtra("data") as ArrayList<SmallCategoryData>

        titleTxt.text = lageTitle.name

        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE
        homeBtn.visibility = View.VISIBLE
        cartBtn.visibility = View.VISIBLE

        //val smallList = list.smallCategory
        mSmallCategoryAdapter = SmallCategoryAdapter(this, data)
        binding.smallCategoryViewPager.adapter = mSmallCategoryAdapter

        TabLayoutMediator(binding.tabLayout, binding.smallCategoryViewPager) { tab, positon ->
            tab.text = data[positon].name
        }.attach()

    }
}