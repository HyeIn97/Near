package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.CategoryRecyclerAdapter
import com.example.near.databinding.FragmentCategoryBinding
import com.example.near.models.BasicResponse
import com.example.near.models.LageCategoryData
import com.example.near.models.SmallCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : BaseFragment() {
    lateinit var binding : FragmentCategoryBinding
    lateinit var mAtapter : CategoryRecyclerAdapter

    //lateinit var binding : FragmentCategoryTestBinding
    lateinit var myIntent : Intent
    val mLageCategoryList = ArrayList<LageCategoryData>()
    val mSmallCategoryList : ArrayList<SmallCategoryData> = arrayListOf()
    val mList : ArrayList<ArrayList<SmallCategoryData>> = arrayListOf()

    /*,
        SmallCategoryData(17, "간식", 4),
        SmallCategoryData(18, "영양제", 4),
        SmallCategoryData(19, "배변용품", 4),
        SmallCategoryData(20, "악세서리", 4)})*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {
        getData()
        //initAdapter()
    }

    fun initAdapter(){
        mAtapter = CategoryRecyclerAdapter(mContext, mLageCategoryList)
        binding.categoryListRecyclerView.adapter = mAtapter
        binding.categoryListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getData(){
        apiList.getAllCategory().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mLageCategoryList.clear()
                    val br = response.body()!!
                    val category = br.data.categories
                    mLageCategoryList.addAll(category)
                    mSmallCategoryList.add(SmallCategoryData(17, "간식", 4))
                    mSmallCategoryList.add(SmallCategoryData(18, "영양제", 4))
                    mSmallCategoryList.add(SmallCategoryData(18, "배변용품", 4))
                    mSmallCategoryList.add(SmallCategoryData(18, "악세서리", 4))
                    Log.d("mSmallCategoryList!~!~!~!!!!!!!!!!!!!", mSmallCategoryList.toString())
                    //mLageCategoryList.add(4, "애견구독", mSmallCategoryList)
                    mAtapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}