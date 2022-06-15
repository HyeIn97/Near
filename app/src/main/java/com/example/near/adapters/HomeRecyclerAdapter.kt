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
import com.example.near.ui.SmallCategoryActivity
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import com.example.near.databinding.ItemHomeListBinding
import com.example.near.databinding.ItemRecyclerviewHomeHeaderBinding
import com.example.near.models.BasicResponse
import com.example.near.models.LageCategoryData
import com.example.near.models.ProductData
import com.example.near.ui.product.ProductDetailPageActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//메인홈 페이지 리싸이클러뷰
class HomeRecyclerAdapter(val mContext: Context, val mProductList: ArrayList<ArrayList<ProductData>>) : /*ArrayList<ArrayList<ProductData>>*/
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1
    lateinit var frag: Fragment
    lateinit var apiList : APIList
    lateinit var retrofit : Retrofit
    val mLageCategoryList = ArrayList<LageCategoryData>()

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
                            val jsonObj = JSONObject(i.toString())
                            val imgUrl = jsonObj.getString("img_url")
                            mImgList.add(imgUrl)
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

            getData()

            headerBinding.foodBtn.setOnClickListener {
                category(0)
                //myIntent = Intent(mContext, SmallCategoryActivity::class.java)
                //mContext.startActivity(myIntent)
            }
            headerBinding.dressBtn.setOnClickListener {
                category(1)
            }
            headerBinding.lifeBtn.setOnClickListener {
                category(2)
            }
            headerBinding.petBtn.setOnClickListener {
                category(3)
            }
        }
    }

    inner class ItemViewHolder(val itemBinding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun itemBind(items:ArrayList<ProductData>){
            Log.d("items",items.toString())
            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
            horizonAdapter.setItemClickListener(object : ProductHorizontalAdapter.ItemClickListener{
                override fun onItemClick(position: Int) {
                    val myIntent = Intent(mContext, ProductDetailPageActivity::class.java)
                    Log.d("mProductList[position]________?????", items[position].toString())
                    myIntent.putExtra("data", items[position])
                    mContext.startActivity(myIntent)
                }
            })
            itemBinding.recyclerViewVertical.adapter = horizonAdapter
            itemBinding.recyclerViewVertical.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                HeaderViewHolder(ItemRecyclerviewHomeHeaderBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
            else -> {
                ItemViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        //val itemCount = mProductList.homePopProduct.mPopProductList.size + mProductList.mSugProductList.size + 1
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

    fun category(item : Int){
        val list = mLageCategoryList[item]
        val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
        myIntent.putExtra("list", list)
        mContext.startActivity(myIntent)
    }

    fun getData(){
        apiList.getAllCategory().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mLageCategoryList.clear()
                    val br = response.body()!!
                    val category = br.data.categories
                    mLageCategoryList.addAll(category)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

}