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
import com.example.near.adapters.CartRecyclerAdapter
import com.example.near.databinding.FragmentCartBinding
import com.example.near.models.BasicResponse
import com.example.near.models.CartData
import com.example.near.models.ProductData
import com.example.near.ui.user.LoginActivity
import com.example.near.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : BaseFragment() {
    lateinit var binding : FragmentCartBinding
    lateinit var mCartAdapter : CartRecyclerAdapter
    var mProductList : ArrayList<ProductData> = arrayListOf() //리스트에 담고 어뎁터에서 itemClik event로 postion값 넘겨서 결제액티비티로 이동
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
        getData()
        initAdapter()
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        if(ContextUtil.getLoginToken(mContext) == ""){
            binding.nonMemberLayout.visibility = View.VISIBLE
        }else{
            binding.cartRecyclerView.visibility = View.VISIBLE
        }
    }

    fun initAdapter(){
        mCartAdapter = CartRecyclerAdapter(mContext, mProductList)
        binding.cartRecyclerView.adapter = mCartAdapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getData(){
       apiList.getCartList().enqueue(object : Callback<BasicResponse>{
           override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                        mProductList.clear()
                        mProductList.addAll(br.data.carts.map { it.product })
                    mCartAdapter.notifyDataSetChanged()
                    initAdapter()
                }
           }

           override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
           }
       })
    }
}