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
import org.w3c.dom.Text

//소분류 Grid 사용해 정렬
class SmallCategoryRecyclerAdapter(val mContext: Context, val mList: ArrayList<ProductData>) :
    RecyclerView.Adapter<SmallCategoryRecyclerAdapter.ItemViewHolder>() {
    lateinit var frag: Fragment
    lateinit var mItemClickListener : ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_grid_product, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
        val img = view.findViewById<ImageView>(R.id.productImg)
        val name = view.findViewById<TextView>(R.id.nameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        val storeName = view.findViewById<TextView>(R.id.storeNameTxt)
        fun bind(item: ProductData) {
            Glide.with(mContext).load(item.img).into(img)
            name.text = item.name
            price.text = item.price.toString() + " 원"
            storeName.text = item.store!!.name
            Log.d("왜 item__________", item.toString())

//            itemView.setOnClickListener {
//                Intent(mContext, ProductDetailPageActivity::class.java).apply {
//                    putExtra("data", item)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { mContext.startActivity(this) }
//            }
        }


    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(itemClickListener : ItemClickListener){
        mItemClickListener = itemClickListener
    }

}