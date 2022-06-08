package com.example.near.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.near.R
import com.example.near.adapters.MainViewPagerAtapter
import com.example.near.adapters.UserInfoActivity
import com.example.near.databinding.ActivityMainBinding
import com.example.near.utils.ContextUtil
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var mPagerAdapter: MainViewPagerAtapter
//    lateinit var drawerBinding : DrawerLayout
//    lateinit var drawerNavBinding : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        nearIcon.visibility = View.VISIBLE
        searchBtn.visibility = View.VISIBLE
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        settingBtn.setOnClickListener {
            initdrawerSetting()
        }
    }

    override fun setValues() {
        mPagerAdapter = MainViewPagerAtapter(this)
        binding.mainViewPager.adapter = mPagerAdapter

        binding.mainViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
                when (position) {
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
                    else -> {
                        if (ContextUtil.getLoginToken(mContext) == "") {
                            settingBtn.visibility = View.GONE
                        } else {
                            settingBtn.visibility = View.VISIBLE
                        }
                        titleTxt.text = "마이페이지"
                        titleTxt.visibility = View.VISIBLE
                        nearIcon.visibility = View.GONE
                        searchBtn.visibility = View.GONE
                        backBtn.visibility = View.GONE
                        homeBtn.visibility = View.GONE
                        cartBtn.visibility = View.GONE
                    }
                }
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.mainViewPager.currentItem = 0
                R.id.category -> binding.mainViewPager.currentItem = 1
                R.id.cart -> binding.mainViewPager.currentItem = 2
                R.id.myPage -> binding.mainViewPager.currentItem = 3
            }
            return@setOnItemSelectedListener true
        }
    }

    fun initdrawerSetting() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.drawerNavMenu.setNavigationItemSelectedListener(this)
        binding.drawerSettingLayout.openDrawer(GravityCompat.END)
    }
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var myIntent : Intent
            when (item!!.itemId) {
                R.id.userInfo -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                    myIntent = Intent(mContext, UserInfoActivity::class.java)
                    startActivity(myIntent)
                }
                R.id.easyPayment -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                }
                R.id.changePw -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                }
            }
            return false
        }

    override fun onBackPressed() {
        if(binding.drawerSettingLayout.isDrawerOpen(GravityCompat.END)){
            binding.drawerSettingLayout.closeDrawers()
        }else {
            super.onBackPressed()
        }
    }
    }