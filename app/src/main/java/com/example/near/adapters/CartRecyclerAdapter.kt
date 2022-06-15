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
import com.example.near.models.CartData
import com.example.near.models.ProductData
import com.example.near.ui.MainActivity

class CartRecyclerAdapter(val mContext : Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<CartRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val productImg = view.findViewById<ImageView>(R.id.productImg)
        val productName = view.findViewById<TextView>(R.id.productNameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        val point = view.findViewById<TextView>(R.id.pointTxt)
        fun bind(item : ProductData){
            Glide.with(mContext).load(item.img).into(productImg)
            productName.text = item.name
            price.text = item.price.toString() + " 원"
            point.text = (item.price / 100).toString() + " 포인트 적립 예상"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_cart_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}