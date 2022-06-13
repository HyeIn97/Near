package com.example.near.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.HomeRecyclerAdapter
import com.example.near.databinding.FragmentHomeBinding
import com.example.near.models.BasicResponse
import com.example.near.models.HomeListData
import com.example.near.models.ProductData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mHomeAdapter: HomeRecyclerAdapter
    var mPopProductList = ArrayList<ProductData>()
    var mSugProductList = ArrayList<ProductData>()
    var mTotalProductList = ArrayList<ArrayList<ProductData>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        initAdapter()
        popularityList()
    }

    fun initAdapter() {
        mHomeAdapter = HomeRecyclerAdapter(mContext, mTotalProductList)
        mHomeAdapter.frag = this
        binding.homeRecyclerView.adapter = mHomeAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun popularityList() {
        apiList.getReviewRanking().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    Log.d("br______", br.toString())
                    mTotalProductList.clear()
                    mPopProductList.clear()
                    mSugProductList.clear()
                    val reviews = br.data.reviews
                    Log.d("reviews_____", reviews.toString())
                    for(i in reviews){
                        if (mPopProductList.size <= 2) {
                            mPopProductList.add(i.product)
                            Log.d("i.product____", i.product.toString())
                        }
                    }
//                    for (i in br.data.reviews) {
//                        val jsonObj = JSONObject(i.toString())
//                        val product = jsonObj.getJSONObject("product")
//                        val id = product.getInt("id")
//                        val name = product.getString("name")
//                        val price = product.getInt("price")
//                        val img = product.getString("image_url")
//                        val popList = ProductData(id, name, price, img)
//                        if (mPopProductList.size <= 2) {
//                            mPopProductList.add(popList)
//                        }
//                    }
                    /*
                        HomeListData().apply {
                            homePopProduct.ProductList.addAll(mPopProductList)
                            homePopProduct.type = "인기제품"
//                        HomeData.setType = food
                    }*/
                    mTotalProductList.add(mPopProductList)
                }
                suggestionList()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun suggestionList() {
        apiList.getAllProductList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    val product = br.data.products
                    for(i in product){
                        if (mSugProductList.size <= 2) {
                            mSugProductList.add(i)
                        }
                    }

//                    for(i in product) {
//                        val jsonObj = JSONObject(i.toString())
//                        val id = jsonObj.getInt("id")
//                        val name = jsonObj.getString("name")
//                        val price = jsonObj.getInt("price")
//                        val img = jsonObj.getString("image_url")
//                        val sugList = ProductData(id, name, price, img)
//                        if (mSugProductList.size <= 2) {
//                            mSugProductList.add(sugList)
//                        }
//                    }
                }
                /*
                HomeListData().apply {
                    homeSugProductList.ProductList.addAll(mSugProductList)
                    homeSugProductList.type = "신상품"
                }*/
                mTotalProductList.add(mSugProductList)
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}