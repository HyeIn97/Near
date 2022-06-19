package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.SearchResultsActivity

class SearchLatelyRecyclerAdapter(val mContext : Context, val mList : ArrayList<String>) : RecyclerView.Adapter<SearchLatelyRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val searchWord = view.findViewById<TextView>(R.id.latelySearchTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val column = LayoutInflater.from(mContext).inflate(R.layout.item_lately_search, parent, false)
        return ItemViewHolder(column)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.searchWord.text = mList[position]
        holder.searchWord.setOnClickListener {
            val myIntent = Intent(mContext, SearchResultsActivity::class.java)
            myIntent.putExtra("word", mList[position])
            mContext.startActivity(myIntent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}