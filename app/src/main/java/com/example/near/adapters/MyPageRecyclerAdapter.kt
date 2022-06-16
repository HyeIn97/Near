package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.models.LageCategoryData
import com.example.near.models.ProductData

//마이페이지 리싸이클러뷰 어댑터
class MyPageRecyclerAdapter(val mContext: Context, val mTitleList : ArrayList<String> /*, val mProductList: ArrayList<ArrayList<ProductData>>*/) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val header = 1
    val item = 2
    lateinit var binding: FragmentMyPageBinding
    lateinit var frag: Fragment
    private val mProductList: ArrayList<ArrayList<ProductData>> = arrayListOf()
    val mLageCategoryList = ArrayList<LageCategoryData>()

    fun addData(list: ArrayList<ArrayList<ProductData>>) {
        println("my_fragment_list_ " + list)
        this.mProductList.addAll(list)
        println("my_fragment_productList_ " + mProductList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ItemViewHolder(FragmentMyPageBinding.inflate(LayoutInflater.from(mContext), parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ItemViewHolder -> holder.itemBind(mProductList[position])
        }
    }

    override fun getItemCount(): Int {
        Log.d("dd","getItemCount " + mProductList.size)
        return mProductList.size - 1 //얘왜 3나오는지 물어보기
    }

    inner class ItemViewHolder(val itemBinding : FragmentMyPageBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun itemBind(items:ArrayList<ProductData>){
            itemBinding.titleTxt.text = mTitleList[position]
            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
            Log.d("dd","list " + items)
            itemBinding.myPageRecyclerView.adapter = horizonAdapter
            itemBinding.myPageRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}