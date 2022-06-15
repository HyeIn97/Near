package com.example.near.ui.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.databinding.ActivityWriteReviewBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReviewActivity : BaseActivity() {
    lateinit var binding : ActivityWriteReviewBinding
    lateinit var data : ProductData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_review)
        data = intent.getSerializableExtra("data") as ProductData
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.saveBtn.setOnClickListener {
            postData()
        }
    }

    override fun setValues() {
        titleTxt.text = "리뷰 등록"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE

        Glide.with(mContext).load(data.img).into(binding.productImg)
        binding.productNameTxt.text = data.name
        binding.priceTxt.text = data.price.toString() + "원"
        binding.pointTxt.text = (data.price / 100).toString() + " 포인트 적립"
    }

    fun postData(){
        val inputTitle = binding.titleEdt.text.toString()
        val inputContent = binding.contentEdt.text.toString()
        val score = binding.ratingBar.rating
        val tagList = binding.tagEdt.text.toString()
        //val thumbnailImg = binding.thumbnailimgAddBtn.
        apiList.postReviewSave(data.id.toString(), inputTitle, inputContent, score, tagList, null, null).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    Toast.makeText(mContext, "정상 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}