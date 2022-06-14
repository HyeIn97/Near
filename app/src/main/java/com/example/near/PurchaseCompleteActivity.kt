package com.example.near

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.near.databinding.ActivityPurchaseCompleteBinding
import com.example.near.models.PaymentData
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity

class PurchaseCompleteActivity : BaseActivity() {
    lateinit var binding : ActivityPurchaseCompleteBinding
    lateinit var data : PaymentData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_complete)
        data = intent.getSerializableExtra("data") as PaymentData
        Log.d("datadatadatadatadatadatadata",data.toString())
    }

    override fun setUpEvents() {

    }

    override fun setValues() {
    }
}