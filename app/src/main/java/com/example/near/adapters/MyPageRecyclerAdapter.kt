package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.models.ProductData

class MyPageRecyclerAdapter(val mContext: Context, val mProductList: ArrayList<ArrayList<ProductData>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val header = 1
    val item = 2
    lateinit var binding: FragmentMyPageBinding
    lateinit var frag: Fragment

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
        return mProductList.size
    }

    inner class HeaderViewHolder(val itemBinding : FragmentMyPageBinding) : RecyclerView.ViewHolder(itemBinding.root){

    }

    inner class ItemViewHolder(val itemBinding : FragmentMyPageBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun itemBind(items:ArrayList<ProductData>){
            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
            Log.d("dd","ddddd")
            itemBinding.myPageRecyclerView.adapter = horizonAdapter
            itemBinding.myPageRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}