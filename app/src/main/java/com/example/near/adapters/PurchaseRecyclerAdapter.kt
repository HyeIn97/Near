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
import com.example.near.models.ProductData
import org.w3c.dom.Text

class PurchaseRecyclerAdapter(val mContext: Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<PurchaseRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val productImg = view.findViewById<ImageView>(R.id.productImg)
        val storeImg = view.findViewById<ImageView>(R.id.storeProfileImg)
        val storeName = view.findViewById<TextView>(R.id.storeNameTxt)
        val productName = view.findViewById<TextView>(R.id.productNameTxt)
        val productPrice = view.findViewById<TextView>(R.id.productPriceTxt)
        fun bind(item : ProductData){
            Glide.with(mContext).load(item.img).into(productImg)
            Glide.with(mContext).load(item.store!!.img).into(storeImg)
            storeName.text = item.store.name
            productName.text = item.name
            productPrice.text = item.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_purchase_product, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}