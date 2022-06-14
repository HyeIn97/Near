package com.example.near.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.near.R
import com.example.near.adapters.CartRecyclerAdapter
import com.example.near.databinding.FragmentCartBinding
import com.example.near.models.BasicResponse
import com.example.near.models.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : BaseFragment() {
    lateinit var binding : FragmentCartBinding
    //lateinit var mCartAdapter : CartRecyclerAdapter
    var mCartList : ArrayList<CartData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
        //initAdapter()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        //getData()
    }

//    fun initAdapter(){
//        mCartAdapter = CartRecyclerAdapter(mContext, mCartList)
//        binding.cartRecyclerView.adapter = mCartAdapter
//        binding.cartRecyclerView.layoutManager = LinearLayoutManager(mContext)
//    }

    fun getData(){
        apiList.getUserCardList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    //mCartList = br.data.carts
                    Log.d("mCartList_________",mCartList.toString())
                    //mCartAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}