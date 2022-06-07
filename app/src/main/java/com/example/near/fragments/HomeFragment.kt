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
import com.example.near.models.ProductData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mHomeAdapter: HomeRecyclerAdapter
    var mProductList = ArrayList<ProductData>()
    var mTotalProductList = ArrayList<ArrayList<ProductData>>()
    var productList = ArrayList<ProductData>()
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
        mHomeAdapter = HomeRecyclerAdapter(mContext, productList)
        mHomeAdapter.frag = this
        binding.homeRecyclerView.adapter = mHomeAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun popularityList() {
        apiList.getReviewRanking().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mProductList.clear()

                    for (i in br.data.reviews) {
                        val jsonObj = JSONObject(i.toString())
                        val product = jsonObj.getJSONObject("product")
                        //Log.d("product", "${product}")
                        val id = product.getInt("id")
                        val name = product.getString("name")
                        val price = product.getInt("price")
                        val img = product.getString("image_url")
                        val popList = ProductData(id, name, price, img)
                        if(mProductList.size <= 2){
                            mProductList.add(popList)
                        }
                        Log.d("mTotalProductList", mProductList.toString())
                    }
                            mTotalProductList.add(mProductList)
                }
                suggestionList()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun suggestionList() {
        apiList.getAllProductList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}