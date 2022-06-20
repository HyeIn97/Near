package com.example.near.ui.product

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.near.MyReceiver
import com.example.near.R
import com.example.near.adapters.SmallCategoryAdapter
import com.example.near.databinding.ActivitySmallCategoryBinding
import com.example.near.fragments.CartFragment
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import com.example.near.ui.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class SmallCategoryActivity : BaseActivity() {
    lateinit var binding : ActivitySmallCategoryBinding
    lateinit var myIntent : Intent
    lateinit var mSmallCategoryAdapter : SmallCategoryAdapter
    lateinit var mReceiveer : BroadcastReceiver
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_small_category)
        position = intent.getIntExtra("position", 0)
        mReceiveer = MyReceiver()
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayout.getTabAt(position)!!.select()
        val filter = IntentFilter()
        filter.addAction(MyReceiver.MainAction)
        filter.addAction(MyReceiver.CartAction)
        registerReceiver(mReceiveer, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiveer)
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        homeBtn.setOnClickListener {
            val myIntent = Intent(MyReceiver.MainAction)
            sendBroadcast(myIntent)
            overridePendingTransition(0, 0)
            ActivityCompat.finishAffinity(this)
            overridePendingTransition(0, 0)
        }
        cartBtn.setOnClickListener {
            val myIntent = Intent(MyReceiver.CartAction)
            sendBroadcast(myIntent)
            overridePendingTransition(0, 0)
            ActivityCompat.finishAffinity(this)
            overridePendingTransition(0, 0)
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