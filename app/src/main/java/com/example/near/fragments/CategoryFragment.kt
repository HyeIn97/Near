package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.SmallCategoryActivity
import com.example.near.databinding.FragmentCategoryTestBinding
import com.example.near.models.BasicResponse
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : BaseFragment() {
    //lateinit var binding : FragmentCategoryBinding
    //lateinit var mAtapter : CategoryRecyclerAdapter

    lateinit var binding : FragmentCategoryTestBinding
    lateinit var myIntent : Intent
    val mLageCategoryList = ArrayList<LageCategoryData>()
    val mSmallCategoryList = ArrayList<SmallCategoryData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_test, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.foodBtn.setOnClickListener {
            category(0)
        }
        binding.lifeBtn.setOnClickListener {
            category(1)
        }
        binding.dressBtn.setOnClickListener {
            category(2)
        }
        binding.petBtn.setOnClickListener {
            category(3)
        }
    }

    override fun setValues() {
        getData()
        //initAdapter()
    }

//    fun initAdapter(){
//        mAtapter = CategoryRecyclerAdapter(mContext, mLageCategoryList)
//        binding.categoryFrRecyclerView.adapter = mAtapter
//        binding.categoryFrRecyclerView.layoutManager = LinearLayoutManager(mContext)
//    }

    fun getData(){
        apiList.getAllCategory().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mLageCategoryList.clear()
                    val br = response.body()!!
                    Log.d("br________", br.data.toString())
                    val category = br.data.categories
                    Log.d("category________", category.toString())
//                    for(i in category){
//                        mSmallCategoryList.addAll(i.smallCategories)
//                    }
                    mLageCategoryList.addAll(category)
                    Log.d("mLageCategoryList________", mLageCategoryList.toString())
                }
        val list = mLageCategoryList[0]
        Log.d("list_____",list.toString())
                //mAtapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun category(item : Int){
        val list = mLageCategoryList[item]
        //val smList = mSmallCategoryList.addAll(list.smallCategory)
        //Log.d("mSmallCategoryList_____",mSmallCategoryList.toString())
        Log.d("값이 가긴한느거 ?",list.toString())
        myIntent = Intent(mContext, SmallCategoryActivity::class.java)
        myIntent.putExtra("list", list)
        startActivity(myIntent)
    }
}