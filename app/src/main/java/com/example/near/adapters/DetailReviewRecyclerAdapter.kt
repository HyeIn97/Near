package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.ReviewData

class DetailReviewRecyclerAdapter(val mContext : Context, val mList : ArrayList<ReviewData>) : RecyclerView.Adapter<DetailReviewRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view : View): RecyclerView.ViewHolder(view){
        val userId = view.findViewById<TextView>(R.id.userId)
        val userTitle = view.findViewById<TextView>(R.id.titleTxt)
        val userScore = view.findViewById<RatingBar>(R.id.ratingBar)
        fun bind(item : ReviewData){
            userId.text = item.user.nickName
            userTitle.text = item.title
            userScore.rating = item.score.toFloat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_product_detail_review, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}