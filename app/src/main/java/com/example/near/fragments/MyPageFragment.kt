package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.near.ui.user.LoginActivity
import com.example.near.R
import com.example.near.adapters.MyPageRecyclerAdapter
import com.example.near.ui.user.SignActivity
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.models.BasicResponse
import com.example.near.models.ProductData
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : BaseFragment() {
    lateinit var myPagebinding : FragmentMyPageBinding
    lateinit var myPageAdapter : MyPageRecyclerAdapter
    var mPopProductList = ArrayList<ProductData>()
    var mSugProductList = ArrayList<ProductData>()
    var mTotalProductList = ArrayList<ArrayList<ProductData>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPagebinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        return myPagebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
        memberCheck()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        initAdapter()
        popularityList()
        suggestionList()
    }

    fun initAdapter() {
        myPageAdapter = MyPageRecyclerAdapter(mContext, mTotalProductList)
        myPageAdapter.frag = this
        myPagebinding.myPageRecyclerView.adapter = myPageAdapter
        myPagebinding.myPageRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun memberCheck(){
        if(ContextUtil.getLoginToken(mContext) == ""){
            ContextUtil.clear(mContext)
            myPagebinding.nonMemberLayout.visibility = View.VISIBLE
            var myIntent: Intent
            myPagebinding.loginBtn.setOnClickListener {
                myIntent = Intent(mContext, LoginActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(myIntent)
            }
            myPagebinding.signBtn.setOnClickListener {
                myIntent = Intent(mContext, SignActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(myIntent)
            }
        }else{
            var userPoint = "0"
            myPagebinding.memberLayout.visibility = View.VISIBLE
                myPagebinding.nickNmaeTxt.text = GlobalData.loginUser!!.nickName
                Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(myPagebinding.profileImg)
            apiList.getUserPoint().enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        val br = response.body()!!
                        userPoint = br.data.point.toString()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })
            myPagebinding.pointTxt.text = userPoint + "원"
        }
    }

    fun popularityList() {
        apiList.getReviewRanking().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mPopProductList.clear()

//                    val reviewData = br.data.reviews
//                    Log.d("reviewData", reviewData.toString())
//                    val productData = reviewData
                    for (i in br.data.reviews) {
                        val jsonObj = JSONObject(i.toString())
                        val product = jsonObj.getJSONObject("product")
                        //Log.d("product", "${product}")
                        val id = product.getInt("id")
                        val name = product.getString("name")
                        val price = product.getInt("price")
                        val img = product.getString("image_url")
                        val popList = ProductData(id, name, price, img)
                        if(mPopProductList.size <= 2){
                            mPopProductList.add(popList)
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
        apiList.getAllProductList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    val productData = br.data.products
                    for(i in productData){
                        val jsonObj = JSONObject(i.toString())
                        val id = jsonObj.getInt("id")
                        val name = jsonObj.getString("name")
                        val price = jsonObj.getInt("price")
                        val img = jsonObj.getString("image_url")
                        val sugList = ProductData(id, name, price, img)
                        if(mSugProductList.size <= 2){
                            mSugProductList.add(sugList)
                        }
                    }
                    mTotalProductList.add(mSugProductList)
                }
                Log.d("mTotalProductList",mTotalProductList.toString())
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}