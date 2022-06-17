package com.example.near.fragments

import android.os.Bundle
import android.provider.Settings
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
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
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
    var mTitleList : ArrayList<String> = arrayListOf()
    var nickStr = ""

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
        initAdapter()
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun onResume() {
        super.onResume()
    }
    override fun setValues() {
        popularityList()
    }

    fun initAdapter() {
        if(ContextUtil.getLoginToken(mContext) == ""){
            mTitleList.add("이런 상품은 어떠세요 ?\n\n신상품순")
        }else{
            nickStr = GlobalData.loginUser!!.nickName + "님 "
            mTitleList.add("${nickStr}이런 상품은 어떠세요 ?\n\n신상품순 ")
        }
        mTitleList.add("인기순")
        mHomeAdapter = HomeRecyclerAdapter(mContext, mTotalProductList, mTitleList)
        mHomeAdapter.frag = this
        binding.homeRecyclerView.adapter = mHomeAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun popularityList() {
        apiList.getReviewRanking().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mTotalProductList.clear()
                    mPopProductList.clear()
                    mSugProductList.clear()
                    val reviews = br.data.reviews
                    for(i in reviews){
                        if (mPopProductList.size <= 5) {
                            mPopProductList.add(i.product)
                        }
                    }
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
                        if (mSugProductList.size <= 5) {
                            mSugProductList.add(i)
                        }
                    }
                }
                mTotalProductList.add(mSugProductList)
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}