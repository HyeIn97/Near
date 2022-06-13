package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.near.databinding.ActivityProductDetailPageBinding
import com.example.near.databinding.ItemProductDetailHeaderBinding

//제품 상세페이지 리싸이클러뷰 어댑터
class ProductDetailPageRecyclerAdapter(val mContext:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            HEADER -> {
                HeaderViewHolder(ItemProductDetailHeaderBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
            else -> {
                ItemViewHolder(ActivityProductDetailPageBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class HeaderViewHolder(val headerBinding : ItemProductDetailHeaderBinding) : RecyclerView.ViewHolder(headerBinding.root){

    }

    inner class ItemViewHolder(val itemBinding : ActivityProductDetailPageBinding) : RecyclerView.ViewHolder(itemBinding.root){

    }
}