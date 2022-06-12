package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.LageCategoryData

class CategoryHorizontalAdapter(val mContext: Context, val item: LageCategoryData) : RecyclerView.Adapter<CategoryHorizontalAdapter.ItemViewHolder>() {
    inner class ItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : LageCategoryData){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_small_categories, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return 2
    }
}