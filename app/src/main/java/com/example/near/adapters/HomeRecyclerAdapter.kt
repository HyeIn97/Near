package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.ui.product.ProductListActivity
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import com.example.near.databinding.ItemHomeListBinding
import com.example.near.databinding.ItemRecyclerviewHomeHeaderBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeRecyclerAdapter(val mContext: Context, val mProductList: ArrayList<ArrayList<ProductData>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1
    lateinit var frag: Fragment
    lateinit var apiList : APIList
    lateinit var retrofit : Retrofit



    inner class HeaderViewHolder(val headerBinding: ItemRecyclerviewHomeHeaderBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun headerBindPage() {
            retrofit = ServerAPI.getRetrofit(mContext)
            apiList = retrofit.create(APIList::class.java)
            var myIntent: Intent
            var mImgList = ArrayList<String>()

            apiList.getBannerImg().enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        mImgList.clear()
                        for(i in br.data.banners){
                            Log.d("i", "${i}")
                            val jsonObj = JSONObject(i.toString())
                            val imgUrl = jsonObj.getString("img_url")
                            mImgList.add(imgUrl)
                            Log.d("mImgList", mImgList.toString())
                        }

                        Glide.with(mContext).load(mImgList[0]).into(headerBinding.imgView1)
                        Glide.with(mContext).load(mImgList[1]).into(headerBinding.imgView2)
                        Glide.with(mContext).load(mImgList[2]).into(headerBinding.imgView3)

                        headerBinding.bannerFlipper.setAutoStart(true);
                        headerBinding.bannerFlipper.setFlipInterval(4000);
                        headerBinding.bannerFlipper.startFlipping();

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }

            })
            headerBinding.foodBtn.setOnClickListener {
                myIntent = Intent(mContext, ProductListActivity::class.java)
                mContext.startActivity(myIntent)
            }
            headerBinding.lifeBtn.setOnClickListener {
                myIntent = Intent(mContext, ProductListActivity::class.java)
                mContext.startActivity(myIntent)
            }
            headerBinding.dressBtn.setOnClickListener {
                myIntent = Intent(mContext, ProductListActivity::class.java)
                mContext.startActivity(myIntent)
            }
            headerBinding.petBtn.setOnClickListener {
                myIntent = Intent(mContext, ProductListActivity::class.java)
                mContext.startActivity(myIntent)
            }
        }
    }

    inner class ItemViewHolder(val itemBinding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun itemBind(items:ArrayList<ProductData>){
            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
            itemBinding.recyclerViewVertical.adapter = horizonAdapter
            itemBinding.recyclerViewVertical.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                HeaderViewHolder(
                    ItemRecyclerviewHomeHeaderBinding.inflate(
                        LayoutInflater.from(
                            mContext
                        ), parent, false
                    )
                )
            }
            else -> {
                ItemViewHolder(
                    ItemHomeListBinding.inflate(
                        LayoutInflater.from(
                            mContext
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mProductList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> HEADER
            else -> ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.headerBindPage()
            }
            is ItemViewHolder -> {
                holder.itemBind(mProductList[position-1])
            }
        }
    }

}