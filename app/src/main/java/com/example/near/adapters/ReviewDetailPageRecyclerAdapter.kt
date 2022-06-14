package com.example.near.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.RepliesData
import com.example.near.models.ReviewData

class ReviewDetailPageRecyclerAdapter(val mContext : Context, val mList : ArrayList<RepliesData>) : RecyclerView.Adapter<ReviewDetailPageRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nickName = view.findViewById<TextView>(R.id.nickNameTxt)
        val comment = view.findViewById<TextView>(R.id.commentTxt)
        val date = view.findViewById<TextView>(R.id.dateTxt)
        fun itemBind(item : RepliesData){
            nickName.text = item.user.nickName
            comment.text = item.content
            date.text = item.date
            //comment.text = item.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_review_comment, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemBind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}