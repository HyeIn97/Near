package com.example.near.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.near.EasyPaymentActivity
import com.example.near.R
import com.example.near.adapters.MainViewPagerAtapter
import com.example.near.ui.user.UserInfoActivity
import com.example.near.databinding.ActivityMainBinding
import com.example.near.fragments.MyPageFragment
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
            //refreshFragment(this, ft)
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

        //replaceFragment(CartFragment())

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.drawerNavMenu.setNavigationItemSelectedListener(this)
        binding.drawerSettingLayout.openDrawer(GravityCompat.END)
    }

    //프레그먼트 변경 함수
//    fun replaceFragment(fragment: Fragment) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fl_container, fragment).commit();
//    }
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
                    myIntent = Intent(mContext, EasyPaymentActivity::class.java)
                    startActivity(myIntent)
                }
                R.id.changePw -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    Toast.makeText(mContext, "되는거?", Toast.LENGTH_SHORT).show()
                    ContextUtil.clear(mContext)
                    myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)
                    finish()
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

    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }
    /*
    프래그먼트 - 프래그먼트 호출할때
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }*/
}