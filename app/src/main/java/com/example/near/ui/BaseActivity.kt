package com.example.near.ui

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext : Context
    lateinit var apiList : APIList
    lateinit var retrofit : Retrofit

    lateinit var nearIcon : ImageView
    lateinit var searchBtn : ImageView
    lateinit var titleTxt : TextView
    lateinit var reviewPageActionBarLayout : LinearLayout
    lateinit var nickNameTxt : TextView
    lateinit var backBtn : ImageView
    lateinit var homeBtn : ImageView
    lateinit var cartBtn : ImageView
    lateinit var settingBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        supportActionBar?.let {
            setCustomActionBar()
        }
        retrofit = ServerAPI.getRetrofit(mContext)
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun setUpEvents()
    abstract fun setValues()

    fun setCustomActionBar(){
        val defActionBar = supportActionBar!!
        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defActionBar.setCustomView(R.layout.custom_action_bar)

        val toolbar = defActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)

        nearIcon = defActionBar.customView.findViewById(R.id.nearIcon)
        searchBtn = defActionBar.customView.findViewById(R.id.searchBtn)
        titleTxt = defActionBar.customView.findViewById(R.id.titleTxt)
        reviewPageActionBarLayout = defActionBar.customView.findViewById(R.id.reviewPageActionBarLayout)
        nickNameTxt = defActionBar.customView.findViewById(R.id.nickNameTxt)
        backBtn = defActionBar.customView.findViewById(R.id.backBtn)
        homeBtn = defActionBar.customView.findViewById(R.id.homeBtn)
        cartBtn = defActionBar.customView.findViewById(R.id.cartBtn)
        settingBtn = defActionBar.customView.findViewById(R.id.settingBtn)
    }
}