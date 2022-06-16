package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import com.example.near.fragments.CartFragment
import com.example.near.models.BasicResponse
import com.example.near.models.CartData
import com.example.near.models.ProductData
import com.example.near.ui.MainActivity
import com.example.near.ui.purchase.PurchaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CartRecyclerAdapter(val mContext : Context, val mList : ArrayList<ProductData>) : RecyclerView.Adapter<CartRecyclerAdapter.ItemViewHolder>() {
    lateinit var apiList : APIList
    lateinit var retrofit : Retrofit
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val productImg = view.findViewById<ImageView>(R.id.productImg)
        val productName = view.findViewById<TextView>(R.id.productNameTxt)
        val price = view.findViewById<TextView>(R.id.priceTxt)
        val point = view.findViewById<TextView>(R.id.pointTxt)
        val delBtn = view.findViewById<Button>(R.id.delBtn)
        val buyBtn = view.findViewById<Button>(R.id.buyBtn)
        fun bind(item : ProductData){
            retrofit = ServerAPI.getRetrofit(mContext)
            apiList = retrofit.create(APIList::class.java)

            Glide.with(mContext).load(item.img).into(productImg)
            productName.text = item.name
            price.text = item.price.toString() + " 원"
            point.text = (item.price / 100).toString() + " 포인트 적립 예상"

            delBtn.setOnClickListener {
                apiList.deleteCart(item.id.toString()).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful){
                            val br = response.body()!!
                            Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                            ((mContext as MainActivity)
                                .supportFragmentManager
                                .findFragmentByTag("f2") as CartFragment)
                                .getData()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    }
                })
            }

            buyBtn.setOnClickListener {
                val myIntent = Intent(mContext, PurchaseActivity::class.java)
                myIntent.putExtra("data", item)
                mContext.startActivity(myIntent)
            }

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