package com.example.near.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.near.DBHelper
import com.example.near.R
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import com.example.near.fragments.HomeFragment
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {
    val CARTFRAGMENT = "cart"
    val MAINFRAGMENT = "main"
    lateinit var mContext : Context
    lateinit var apiList : APIList
    lateinit var retrofit : Retrofit

    lateinit var nearIcon : ImageView
    lateinit var searchBtn : ImageView
    lateinit var titleTxt : TextView
    lateinit var reviewPageActionBarLayout : LinearLayout
    lateinit var backBtn : ImageView
    lateinit var homeBtn : ImageView
    lateinit var cartBtn : ImageView
    lateinit var settingBtn : ImageView
    lateinit var nickNameTxt : TextView
    lateinit var userImg : ImageView

    lateinit var dbHelpler : DBHelper
    lateinit var database : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        supportActionBar?.let {
            setCustomActionBar()
        }
        retrofit = ServerAPI.getRetrofit(mContext)
        apiList = retrofit.create(APIList::class.java)
        dbHelpler = DBHelper(mContext, "near.db", null, 1)
        database = dbHelpler.writableDatabase
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
        backBtn = defActionBar.customView.findViewById(R.id.backBtn)
        homeBtn = defActionBar.customView.findViewById(R.id.homeBtn)
        cartBtn = defActionBar.customView.findViewById(R.id.cartBtn)
        settingBtn = defActionBar.customView.findViewById(R.id.settingBtn)
        userImg = defActionBar.customView.findViewById(R.id.userImg)
        nickNameTxt = defActionBar.customView.findViewById(R.id.nickNameTxt)

    }

    fun setFragment(value : String){
        val transaction = supportFragmentManager.beginTransaction()
        when(value){
            "home" -> {
                Log.d("타긴하는거?", "웅애 액티비티")
                transaction.replace(R.id.homeFrameLayout, HomeFragment()).commit()
            }
            CARTFRAGMENT -> {

            }
        }
    }
}