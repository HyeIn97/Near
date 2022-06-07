package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.ProductData

class HomeHorizontalAdapter(val mContext : Context, val mList : List<ProductData>) : RecyclerView.Adapter<HomeHorizontalAdapter.ItemViewHolder>() {
    lateinit var frag: Fragment
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : ProductData){
        //받은데이터를 자리에 맞게 뿌려줘야함
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent ,false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return 3
    }


}