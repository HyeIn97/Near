package com.example.near.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.models.ProductData

//메인 + 마이페이지 Grid 사용안하고 상품 가로 정렬
class ProductHorizontalAdapter(val mContext : Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<ProductHorizontalAdapter.ItemViewHolder>() {
    lateinit var mItemClickListener : ItemClickListener

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
        val img = view.findViewById<ImageView>(R.id.productImg)
        val name = view.findViewById<TextView>(R.id.nameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        fun bind(item : ProductData){
            Glide.with(mContext).load(item.img).into(img)
            name.text = item.name
            price.text = item.price.toString() + " 원"
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
        Log.d("가로 리스트 사이즈", mList.size.toString())
        return mList.size
    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(itemClickListener : ItemClickListener){
        mItemClickListener = itemClickListener
    }


}