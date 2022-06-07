package com.example.near.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.near.R
import com.example.near.adapters.MainViewPagerAtapter
import com.example.near.databinding.ActivityMainBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var mPagerAdapter : MainViewPagerAtapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        nearIcon.visibility = View.VISIBLE
        searchBtn.visibility = View.VISIBLE
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
    }

    override fun setValues() {
        mPagerAdapter = MainViewPagerAtapter(this)
        binding.mainViewPager.adapter = mPagerAdapter

        binding.mainViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
                when (position){
                    0 -> {
                        nearIcon.visibility = View.VISIBLE
                        searchBtn.visibility = View.VISIBLE
                        backBtn.visibility = View.GONE
                        homeBtn.visibility = View.GONE
                        cartBtn.visibility = View.GONE
                        settingBtn.visibility = View.GONE
                        titleTxt.visibility = View.GONE
                    }
                    1 -> {
                        titleTxt.text = "카테고리"
                        titleTxt.visibility = View.VISIBLE
                        searchBtn.visibility = View.VISIBLE
                        nearIcon.visibility = View.GONE
                        backBtn.visibility = View.GONE
                        homeBtn.visibility = View.GONE
                        cartBtn.visibility = View.GONE
                        settingBtn.visibility = View.GONE
                    }
                    2 -> {
                        titleTxt.text = "장바구니"
                        titleTxt.visibility = View.VISIBLE
                        backBtn.visibility = View.VISIBLE
                        nearIcon.visibility = View.GONE
                        searchBtn.visibility = View.GONE
                        homeBtn.visibility = View.GONE
                        cartBtn.visibility = View.GONE
                        settingBtn.visibility = View.GONE
                    }
                    else ->{
                        titleTxt.text = "마이페이지"
                        titleTxt.visibility = View.VISIBLE
                        settingBtn.visibility = View.VISIBLE
                        nearIcon.visibility = View.GONE
                        searchBtn.visibility = View.GONE
                        backBtn.visibility = View.GONE
                        homeBtn.visibility = View.GONE
                        cartBtn.visibility = View.GONE
                        settingBtn.visibility = View.GONE
                    }
                }
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> binding.mainViewPager.currentItem = 0
                R.id.category -> binding.mainViewPager.currentItem = 1
                R.id.cart -> binding.mainViewPager.currentItem = 2
                R.id.myPage -> binding.mainViewPager.currentItem = 3
            }
            return@setOnItemSelectedListener true
        }
    }
}