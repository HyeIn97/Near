package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.models.ProductData

class HomeHorizontalAdapter(val mContext : Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<HomeHorizontalAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val img = view.findViewById<ImageView>(R.id.productImg)
        val name = view.findViewById<TextView>(R.id.nameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        fun bind(item : ProductData){
        //받은데이터를 자리에 맞게 뿌려줘야함
            Glide.with(mContext).load(item.img).into(img)
            name.text = item.name
            price.text = item.price.toString()
            Log.d("name.text", name.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent ,false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return 3
    }


}