package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import com.example.near.ui.SmallCategoryActivity

class HomeCategoryRecyclerAdapter (val mContext: Context, val mList : LageCategoryData, val mSmallList : ArrayList<SmallCategoryData>) : RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.homeSmallTitleTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_samll_category_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.title.visibility = View.VISIBLE
        holder.title.text = mSmallList[position].name

        holder.title.setOnClickListener {
            val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
            myIntent.putExtra("lageData", mList)
            myIntent.putExtra("data", mSmallList)
            myIntent.putExtra("position", mSmallList.indexOf(mSmallList[position]))
            mContext.startActivity(myIntent)
        }
    }

    override fun getItemCount(): Int {
        return mSmallList.size
    }
}