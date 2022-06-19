package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.ProductData

class SearchRakingRecyclerAdapter(val mContext: Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<SearchRakingRecyclerAdapter.ItemViewHolder>(){
    var num = 1
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val rakingNum = view.findViewById<TextView>(R.id.rakingNumTxt)
        val pop = view.findViewById<TextView>(R.id.popTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_popular_search, parent, false)


        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.rakingNum.text = num++.toString()
        Log.d("holder.rakingNum.text", holder.rakingNum.text.toString())
        holder.pop.text = mList[position].name
        Log.d("holder.pop.text",holder.pop.text.toString())
    }

    override fun getItemCount(): Int {
        Log.d("mList.size",mList.size.toString())
        return mList.size
    }
}