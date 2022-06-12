package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.databinding.ItemCategoryBinding
import com.example.near.databinding.ItemSamllCategoryListBinding
import com.example.near.models.LageCategoryData

class CategoryRecyclerAdapter(val mContext : Context, val mList : ArrayList<LageCategoryData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val LAGECATEGORIE = 0
    val ITEMLIST = 1
    //val SMALLCATEGORIE = 2
    lateinit var frg : Fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LAGECATEGORIE -> {
                LagecategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
            else -> {
                ItemListViewHolder(ItemSamllCategoryListBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
//            else ->{
//                SmallcategoryViewHolder(ItemSmallCategoriesBinding.inflate(LayoutInflater.from(mContext), parent, false))
//            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LagecategoryViewHolder -> {
                holder.headerBind(mList[position])
            }
            is ItemListViewHolder -> {
                holder.itemBind(mList[position])
            }
            //is SmallcategoryViewHolder -> {}
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> LAGECATEGORIE
            else -> ITEMLIST
            //else -> SMALLCATEGORIE
        }
    }

    inner class LagecategoryViewHolder(val headerBinding : ItemCategoryBinding) : RecyclerView.ViewHolder(headerBinding.root) {
        fun headerBind(item : LageCategoryData){

        }
    }

    inner class ItemListViewHolder(val itemListBinding : ItemSamllCategoryListBinding) : RecyclerView.ViewHolder(itemListBinding.root){
        fun itemBind(items: LageCategoryData){
            val horizontalAdapter = CategoryHorizontalAdapter(mContext, items)
            itemListBinding.smallCGTitleRecyclerView.adapter = horizontalAdapter
            itemListBinding.smallCGTitleRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

//    inner class SmallcategoryViewHolder(val smallBinding : ItemSmallCategoriesBinding) : RecyclerView.ViewHolder(smallBinding.root){
//        fun smallBind(items: ArrayList<LageCategoriesData>){
//            val horizontalAdapter = CategoryHorizontalAdapter(mContext, items)
//
//        }
//    }
    fun addData(list: ArrayList<LageCategoryData>){
        this.mList.addAll(list)
    }
}
