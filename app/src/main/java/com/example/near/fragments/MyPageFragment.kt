package com.example.near.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.ui.user.LoginActivity
import com.example.near.R
import com.example.near.databinding.DrawerSettingBinding
import com.example.near.ui.user.SignActivity
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : BaseFragment() {
    lateinit var myPagebinding : FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPagebinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        return myPagebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
        memberCheck()

    }

    override fun setupEvents() {
    }

    override fun setValues() {

    }

    fun memberCheck(){
        if(ContextUtil.getLoginToken(mContext) == ""){
            ContextUtil.clear(mContext)
            myPagebinding.nonMemberLayout.visibility = View.VISIBLE
            var myIntent: Intent
            myPagebinding.loginBtn.setOnClickListener {
                myIntent = Intent(mContext, LoginActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(myIntent)
            }
            myPagebinding.signBtn.setOnClickListener {
                myIntent = Intent(mContext, SignActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(myIntent)
            }
        }else{
            var userPoint = "0"
            myPagebinding.memberLayout.visibility = View.VISIBLE
                myPagebinding.nickNmaeTxt.text = GlobalData.loginUser!!.nickName
                Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(myPagebinding.profileImg)
            apiList.getUserPoint().enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        val br = response.body()!!
                        userPoint = br.data.point.toString()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })
            myPagebinding.pointTxt.text = userPoint + "원"
        }
    }


}