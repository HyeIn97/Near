package com.example.near.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.api.APIList
import com.example.near.api.ServerAPI
import com.example.near.models.BasicResponse
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import com.example.near.ui.SmallCategoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CategorySubRecyclerAdapter(val mContext: Context, val mList: ArrayList<SmallCategoryData>, val mLage : LageCategoryData) : RecyclerView.Adapter<CategorySubRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val smallTitle = view.findViewById<TextView>(R.id.smallTitleTxt)
        fun bind(item : SmallCategoryData){
            smallTitle.text = item.name
            smallTitle.setOnClickListener {
                val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
                myIntent.putExtra("lageData", mLage)
                myIntent.putExtra("data", mList)
                myIntent.putExtra("position", mList.indexOf(mList[position]))
                mContext.startActivity(myIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_samll_category_list, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
//        holder.smallTitle.setOnClickListener {
//            val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
//            myIntent.putExtra("data", mList[position])
//            Log.d("datadatadatadatadatadatav", mList[position].toString())
//            mContext.startActivity(myIntent)
//        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    /*fun getData(id : Int) : Int{
        retrofit = ServerAPI.getRetrofit(mContext)
        apiList = retrofit.create(APIList::class.java)
        var count : Int = 0
        apiList.getSmallCategoryList(id).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    val product = br.data.products
                    count = product.size
                    Log.d("count", count.toString())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
        return count
    }*/

    fun category(item : Int){
        val list = mList[item]
        val myIntent = Intent(mContext, SmallCategoryActivity::class.java)
        myIntent.putExtra("data", list)
        mContext.startActivity(myIntent)
    }
}