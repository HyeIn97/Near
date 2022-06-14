package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.models.ReviewData

class ReviewListRecyclerAdapter(val mContext : Context, val mList : ArrayList<ReviewData>) : RecyclerView.Adapter<ReviewListRecyclerAdapter.ItemViewHolder>() {
    lateinit var frag: Fragment
    lateinit var mItemClickListener : ItemClickListener

    inner class ItemViewHolder(view : View): RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
        val userId = view.findViewById<TextView>(R.id.userId)
        val userTitle = view.findViewById<TextView>(R.id.title)
        val userScore = view.findViewById<RatingBar>(R.id.ratingBar)
        val reviewImg = view.findViewById<ImageView>(R.id.reviewImg)

        fun bind(item : ReviewData){
            Log.d("tiem______", item.toString())
            Glide.with(mContext).load(item.img).into(reviewImg)
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
        Log.d("mList.size_____", mList.size.toString())
        return mList.size
    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(itemClickListener : ItemClickListener){
        mItemClickListener = itemClickListener
    }
}