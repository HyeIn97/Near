package com.example.near.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.LageCategoryData

class CategoryRecyclerAdapter(val mContext : Context, val mList : ArrayList<LageCategoryData>) : RecyclerView.Adapter<CategoryRecyclerAdapter.ItemViewHolder>(){
   lateinit var subRecyclerAdapter : CategorySubRecyclerAdapter
   var sparseArray = SparseArray<Boolean>() //ture일때 smallTitle == VISIBLE, false일때 smallTitle == GONE

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val lageLayout = view.findViewById<LinearLayout>(R.id.lageCategoryLayout) //대분류 레이아웃
        val lageImg = view.findViewById<ImageView>(R.id.lageCategoryImg)
        val lageTitle = view.findViewById<TextView>(R.id.LageCategoryTitleTxt)
        val smallTitle = view.findViewById<RecyclerView>(R.id.smallCategoryListRecyclerView) //대분류 아래 나올 소분류 recyclerView (visibility = gone)
        val nonClick = view.findViewById<ImageView>(R.id.nonClickBtn)
        val click = view.findViewById<ImageView>(R.id.clickBtn)

        @RequiresApi(Build.VERSION_CODES.S)
        fun bind(item: LageCategoryData){
            if(sparseArray[adapterPosition] == null){
                sparseArray.put(adapterPosition, false) //smallTitle 다 gone 이기 때문에 false로 list 만들어줌
            }

            lageTitle.text = item.name

            lageLayout.setOnClickListener { //대분류 눌렀을 때
                when(smallTitle.visibility){
                    View.VISIBLE -> {
                        sparseArray[adapterPosition] = false
                        smallTitle.visibility = View.GONE
                        click.visibility = View.GONE
                        nonClick.visibility = View.VISIBLE

                    }
                    View.GONE -> {
                        sparseArray[adapterPosition] = true
                        smallTitle.visibility = View.VISIBLE
                        click.visibility = View.VISIBLE
                        nonClick.visibility = View.GONE
                    }
                }
            }
        }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_lage_cartegory, parent, false)
        return ItemViewHolder(row)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
        subRecyclerAdapter = CategorySubRecyclerAdapter(mContext, mList[position].smallCategory, mList[position])
        Log.d("닌 뭐길래 가능함? mList", mList.toString())
        holder.smallTitle.adapter = subRecyclerAdapter
        holder.smallTitle.layoutManager = GridLayoutManager(mContext, 2)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
