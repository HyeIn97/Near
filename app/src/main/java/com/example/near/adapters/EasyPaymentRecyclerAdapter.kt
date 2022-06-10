package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.CardData

class EasyPaymentRecyclerAdapter(val mContext : Context, val mList : ArrayList<CardData>) : RecyclerView.Adapter<EasyPaymentRecyclerAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val cardNick = view.findViewById<TextView>(R.id.cardNickTxt)
        val cardNum = view.findViewById<TextView>(R.id.cardNumTxt)
        val cardPeriod = view.findViewById<TextView>(R.id.cardPeriodTxt)
        fun bind(item: CardData){
            cardNick.text = item.cardNick
            cardNum.text = item.cardNum
            cardPeriod.text = item.cardPeriod
        }
    }

}