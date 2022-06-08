package com.example.near

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.ui.MainActivity
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {

    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
            val myIntent = Intent(mContext, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(myIntent)
            overridePendingTransition(0, 0)
            finish()
        },2500)

        if(ContextUtil.getAutoLogin(mContext) && ContextUtil.getLoginToken(mContext) != ""){
            apiList.getUserInfo().enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                        if(response.isSuccessful){
                            val br = response.body()!!
                            GlobalData.loginUser = br.data.user
                        }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })
            Log.d("ContextUtil.getLoginToken(mContext)",ContextUtil.getLoginToken(mContext))
            Log.d("ContextUtil.getAutoLogin(mContext)",ContextUtil.getAutoLogin(mContext).toString())
        }else{
            ContextUtil.clear(mContext)
        }
    }
}