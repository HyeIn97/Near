package com.example.near.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.adapters.SmallCategoryRecyclerAdapter
import com.example.near.databinding.FragmentHomeBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmallCategoryFragment(val smallId : Int) : BaseFragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var mSmallCategoryAdapter : SmallCategoryRecyclerAdapter
    lateinit var mProductList : ArrayList<ProductData>
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
        Log.d("이거 중요하다 !!! 아이디 바뀌나 확인 긔 !!!", smallId.toString())
    }
    override fun setupEvents() {

    }

    override fun setValues() {
        getData()
    }

    fun initAdapter(){
        //mSmallCategoryAdapter = SmallCategoryAdapter(mContext, )
    }

    fun getData(){
        //얘 왜 초기화 안됐다고 뜨는지 봐야됨 ㅡㅡ
        apiList.getSmallCategoryList(smallId).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    val product = br.data.products
                    Log.d("product_____", product.toString())
                }else{
                    val errBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errBodyStr)
                    val message = jsonObj.getString("message")
                    Log.d("에러임", message)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}