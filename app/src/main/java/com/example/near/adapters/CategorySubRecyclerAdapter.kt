package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import com.example.near.ui.SmallCategoryActivity

class CategorySubRecyclerAdapter(val mContext: Context, val mList: ArrayList<SmallCategoryData>, val mLage : LageCategoryData) : RecyclerView.Adapter<CategorySubRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val smallTitle = view.findViewById<TextView>(R.id.smallTitleTxt)
        fun bind(item : SmallCategoryData){
            smallTitle.text = item.name
            smallTitle.visibility = View.VISIBLE
            smallTitle.setOnClickListener {
                val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
                myIntent.putExtra("lageData", mLage)
                myIntent.putExtra("data", mList)
                myIntent.putExtra("position", mList.indexOf(mList[position]))
                mContext.startActivity(myIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_samll_category_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}