package com.example.near

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.near.ui.BaseActivity

class EasyPaymentActivity : BaseActivity() {
    lateinit var binding :
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_payment)
    }
}