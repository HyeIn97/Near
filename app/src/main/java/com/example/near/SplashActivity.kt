package com.example.near

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.near.ui.BaseActivity
import com.example.near.ui.MainActivity

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
    }
}