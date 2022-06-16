package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.models.PaymentData
import com.example.near.ui.review.WriteReviewActivity
import java.text.SimpleDateFormat

class PurchaseListRecyclerAdapter (val mContext : Context, val mList : ArrayList<PaymentData>) : RecyclerView.Adapter<PurchaseListRecyclerAdapter.ItemViewHolder>() {
    lateinit var mItemClickListener : ItemClickListener
    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
        val buyDate = view.findViewById<TextView>(R.id.buyDateTxt)
        val product = view.findViewById<ImageView>(R.id.productImg)
        val productName = view.findViewById<TextView>(R.id.productNameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        val point = view.findViewById<TextView>(R.id.pointTxt)
        val reviewBtn = view.findViewById<Button>(R.id.writeReviewBtn)
        fun bind(item : PaymentData){
            val form = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.date)
            val sdr = SimpleDateFormat("yy-MM-dd")
            buyDate.text = sdr.format(form)
            Glide.with(mContext).load(item.subscription.product.img).into(product)
            productName.text = item.subscription.product.name
            price.text = item.subscription.product.price.toString() + " 원"
            point.text = (item.subscription.product.price / 100).toString()

            reviewBtn.setOnClickListener {
                val myIntent = Intent(mContext, WriteReviewActivity::class.java)
                myIntent.putExtra("data", item.subscription.product)
                mContext.startActivity(myIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_purchase_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(itemClickListener : ItemClickListener){
        mItemClickListener = itemClickListener
    }

}