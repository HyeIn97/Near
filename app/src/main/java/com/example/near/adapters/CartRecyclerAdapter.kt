package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.databinding.ItemCartFooterBinding
import com.example.near.databinding.ItemCartHeaderBinding
import com.example.near.databinding.ItemCartListBinding
import com.example.near.models.CartData
import com.example.near.ui.MainActivity

class CartRecyclerAdapter(val mContext : Context, val mList : ArrayList<CartData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1
    val FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            HEADER -> {
                HeaderViewHolder(ItemCartHeaderBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
            FOOTER -> {
                FooterViewHolder(ItemCartFooterBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
            else -> {
                ItemViewHolder(ItemCartListBinding.inflate(LayoutInflater.from(mContext), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> {
                holder.headerBind()
            }
            is ItemViewHolder -> {
                holder.itemBind(mList[position])
            }
            is FooterViewHolder -> {
                holder.footerBind()
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> HEADER
            1 -> ITEM
            else -> FOOTER
        }
    }

    inner class HeaderViewHolder(val headerBinding : ItemCartHeaderBinding) : RecyclerView.ViewHolder(headerBinding.root){
        fun headerBind(){
            (mContext as MainActivity)
            headerBinding.allCkBox.setOnClickListener {

            }
            headerBinding.allUnCkCartBtn.setOnClickListener {

            }
        }
    }

    inner class ItemViewHolder(val itemViewBinding : ItemCartListBinding) : RecyclerView.ViewHolder(itemViewBinding.root){
        fun itemBind(item : CartData){
            Glide.with(mContext).load(item.product.img).into(itemViewBinding.productImg)
            itemViewBinding.productNameTxt.text = item.product.name
            itemViewBinding.productPriceTxt.text = item.product.price.toString()
        }
    }
    inner class FooterViewHolder(val footerBinding : ItemCartFooterBinding) : RecyclerView.ViewHolder(footerBinding.root){
        fun footerBind(){
            //for()
        }
    }
}