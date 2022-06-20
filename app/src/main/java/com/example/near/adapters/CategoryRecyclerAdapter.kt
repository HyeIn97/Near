package com.example.near.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.text.Transliterator
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
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.near.R
import com.example.near.models.LageCategoryData

class CategoryRecyclerAdapter(val mContext: Context, val mList: ArrayList<LageCategoryData>) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.ItemViewHolder>() {
    lateinit var subRecyclerAdapter: CategorySubRecyclerAdapter
    var drawableList : ArrayList<Drawable> = arrayListOf()
    var prePosition = -1

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodIcon = getDrawable(mContext, R.drawable.food_icon)
        val dressIcon = getDrawable(mContext, R.drawable.dress_icon)
        val lifeIcon = getDrawable(mContext, R.drawable.life_icon)
        val lageLayout = view.findViewById<LinearLayout>(R.id.lageCategoryLayout) //대분류 레이아웃
        val lageImg = view.findViewById<ImageView>(R.id.lageCategoryImg)
        val lageTitle = view.findViewById<TextView>(R.id.LageCategoryTitleTxt)
        val smallTitle = view.findViewById<RecyclerView>(R.id.smallCategoryListRecyclerView) //대분류 아래 나올 소분류 recyclerView (visibility = gone)
        val nonClick = view.findViewById<ImageView>(R.id.nonClickBtn)
        val click = view.findViewById<ImageView>(R.id.clickBtn)

        @RequiresApi(Build.VERSION_CODES.S)
        fun bind(item: LageCategoryData, thisPosition: Int) {
            drawableList.add(foodIcon!!)
            drawableList.add(dressIcon!!)
            drawableList.add(lifeIcon!!)

            lageTitle.text = item.name
            lageImg.setImageDrawable(drawableList[position])

            if(prePosition != thisPosition){
                smallTitle.visibility = View.GONE
                click.visibility = View.GONE
                nonClick.visibility = View.VISIBLE
            }

            lageLayout.setOnClickListener {
                when(smallTitle.visibility) {
                    View.VISIBLE -> {
                        smallTitle.visibility = View.GONE
                        click.visibility = View.GONE
                        nonClick.visibility = View.VISIBLE
                        prePosition = -1
                    }
                    View.GONE -> {
                        smallTitle.visibility = View.VISIBLE
                        click.visibility = View.VISIBLE
                        nonClick.visibility = View.GONE
                        prePosition = thisPosition
                    }
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.item_lage_cartegory, parent, false)
        return ItemViewHolder(row)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position], position)
        subRecyclerAdapter = CategorySubRecyclerAdapter(mContext, mList[position].smallCategory, mList[position])
        holder.smallTitle.adapter = subRecyclerAdapter
        holder.smallTitle.layoutManager = GridLayoutManager(mContext, 2)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
