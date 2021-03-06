package com.example.near.adapters

import android.content.ClipData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.RepliesData
import com.example.near.models.ReviewData
import java.text.SimpleDateFormat

class ReviewDetailPageRecyclerAdapter(val mContext : Context, val mList : ArrayList<RepliesData>) : RecyclerView.Adapter<ReviewDetailPageRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nickName = view.findViewById<TextView>(R.id.nickNameTxt)
        val comment = view.findViewById<TextView>(R.id.commentTxt)
        val date = view.findViewById<TextView>(R.id.dateTxt)
        val nonReplies = view.findViewById<TextView>(R.id.nonRepliesTxt)
        val replies = view.findViewById<LinearLayout>(R.id.repliesLayout)
        fun itemBind(item : RepliesData){
            Log.d("item~!~!~!~!~!~!~!~!~!~!", item.toString())
            if(item != null && !item.equals("")){
                replies.visibility = View.VISIBLE
                nickName.text = item.user.nickName
                comment.text = item.content
                val form = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.date)
                val sdr = SimpleDateFormat("yy-MM-dd")
                date.text = sdr.format(form)
                Log.d("_________item", item.toString())
            }else{
                nonReplies.visibility = View.VISIBLE
                Log.d("_________item", item.toString())
            }

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