package com.example.near.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.models.PaymentData

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
        fun bind(item : PaymentData){
            buyDate.text = item.date
            Glide.with(mContext).load(item.subscription.product.img).into(product)
            productName.text = item.subscription.product.name
            price.text = item.subscription.product.price.toString()
            point.text = (item.subscription.product.price / 100).toString()
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