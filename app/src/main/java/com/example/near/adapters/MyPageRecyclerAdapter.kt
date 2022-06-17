package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.databinding.ItemHomeListBinding
import com.example.near.models.LageCategoryData
import com.example.near.models.ProductData

//마이페이지 리싸이클러뷰 어댑터
class MyPageRecyclerAdapter(val mContext: Context, val mTitleList: ArrayList<String>) : RecyclerView.Adapter<MyPageRecyclerAdapter.ItemViewHolder>() {

    private val mProductList: ArrayList<ArrayList<ProductData>> = arrayListOf()

    fun addData(list: ArrayList<ArrayList<ProductData>>) {
        this.mProductList.addAll(list)
    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val holder = ItemViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(mContext), parent, false))
//        return holder
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is ItemViewHolder -> holder.itemBind(mProductList[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        Log.d("마이페이지 getItemCount", mProductList.size.toString())
//        return mProductList.size //얘왜 3나오는지 물어보기
//    }
//
//    //    inner class ItemViewHolder(val itemBinding : FragmentMyPageBinding) : RecyclerView.ViewHolder(itemBinding.root){
////        fun itemBind(items:ArrayList<ProductData>){
////            itemBinding.titleTxt.text = mTitleList[position]
////            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
////            Log.d("dd","list " + items)
////            itemBinding.myPageRecyclerView.adapter = horizonAdapter
////            itemBinding.myPageRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
////        }
////    }
//    inner class ItemViewHolder(val itemBinding: ItemHomeListBinding) :
//        RecyclerView.ViewHolder(itemBinding.root) {
//        fun itemBind(items: ArrayList<ProductData>) {
//            itemBinding.titleTxt.text = mTitleList[position]
//            Log.d("마이페이지에서 items:ArrayList<ProductData>",items.toString())
//            Log.d("마이페이지에서 items:ArrayList<ProductData> 사이즈",items.size.toString())
//            val horizonAdapter = ProductHorizontalAdapter(mContext, items)
//            Log.d("dd", "list " + items)
//            itemBinding.recyclerViewVertical.adapter = horizonAdapter
//            itemBinding.recyclerViewVertical.layoutManager =
//                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
//        }
//    }

    inner class ItemViewHolder(view : View): RecyclerView.ViewHolder(view){
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVertical)
        val title = view.findViewById<TextView>(R.id.titleTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_home_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val horizonAdapter = ProductHorizontalAdapter(mContext, mProductList[position])
            holder.title.text = mTitleList[position]
            holder.recyclerView.adapter = horizonAdapter
            holder.recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }
}