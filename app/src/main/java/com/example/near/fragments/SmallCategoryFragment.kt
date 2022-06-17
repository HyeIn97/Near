package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.near.R
import com.example.near.adapters.SmallCategoryRecyclerAdapter
import com.example.near.databinding.FragmentHomeBinding
import com.example.near.databinding.FragmentSmallCategoryBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.models.StoreData
import com.example.near.ui.product.ProductDetailPageActivity
import com.example.near.ui.user.ModifyActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmallCategoryFragment(val smallId : Int) : BaseFragment() {
    lateinit var binding : FragmentSmallCategoryBinding
    lateinit var mSmallCategoryAdapter : SmallCategoryRecyclerAdapter
    val mProductList = ArrayList<ProductData>()
    val mDetailData = ArrayList<ProductData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_small_category, container, false)
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
        initAdapter()
    }

    fun initAdapter(){
        mSmallCategoryAdapter = SmallCategoryRecyclerAdapter(mContext, mProductList)
        mSmallCategoryAdapter.frag = this
        mSmallCategoryAdapter.setItemClickListener(object : SmallCategoryRecyclerAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {
                val myIntent = Intent(mContext, ProductDetailPageActivity::class.java)
                Log.d("mProductList[position]________", mProductList[position].toString())
                myIntent.putExtra("data", mProductList[position])
                startActivity(myIntent)
            }
        })
        binding.smallCategoryRecyclerView.adapter = mSmallCategoryAdapter
        binding.smallCategoryRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
    }

    fun getData(){
        apiList.getSmallCategoryList(smallId).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    mProductList.clear()
                    val br = response.body()!!
                    val product = br.data.products
                    for(i in 0 until product.size){
                        val aa = product.get(i)
                        mProductList.add(aa)
                    }
                    Log.d("product_____!!!!!!", product.toString())

                    mProductList.add(ProductData(101, "이진영", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"),
                        null))
                    mProductList.add(ProductData(101, "노시환", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "문동주", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "정은원", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "하주석", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "김인환", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "박정현", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "강재민", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "이민우", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    mProductList.add(ProductData(101, "김민우", 30000, "https://pds.joins.com/news/component/htmlphoto_mmdata/201501/08/htm_20150108173856c010c011.jpg", StoreData(1, "2번 가게", "https://s3.ap-northeast-2.amazonaws.com/gudocin-images/https://catholicoutlook.org/wp-content/uploads/2020/03/To-clothe-the-naked-lauren-fleischmann-R2aodqJn3b8-unsplash-A.jpg?w=640\\r\\n"), null))
                    Log.d("mProductList_____!!!!!!", mProductList.toString())
                    mSmallCategoryAdapter.notifyDataSetChanged()
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