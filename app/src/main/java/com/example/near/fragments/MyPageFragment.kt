package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.near.ui.purchase.PurchaseListActivity
import com.example.near.ui.user.LoginActivity
import com.example.near.R
import com.example.near.ui.review.UserReviewListActivity
import com.example.near.adapters.MyPageRecyclerAdapter
import com.example.near.ui.user.SignActivity
import com.example.near.databinding.FragmentMyPageBinding
import com.example.near.models.*
import com.example.near.utils.ContextUtil
import com.example.near.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : BaseFragment() {
    val RESULT_OK = 1
    lateinit var myPagebinding: FragmentMyPageBinding
    lateinit var myPageAdapter: MyPageRecyclerAdapter
    var mPopProductList = ArrayList<ProductData>()
    var mSugProductList = ArrayList<ProductData>()
    var mTotalProductList = ArrayList<ArrayList<ProductData>>()
    var mSubscriptionList: ArrayList<PaymentData> = arrayListOf()
    var mReivewList: ArrayList<ReviewData> = arrayListOf()
    var mTitleList : ArrayList<String> = arrayListOf()
    lateinit var myIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPagebinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        return myPagebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupEvents()
        setValues()
        memberCheck()
        //refresh(this, parentFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        if (ContextUtil.getLoginToken(mContext) != "") {
            myPagebinding.nickNmaeTxt.text = GlobalData.loginUser!!.nickName
            Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(myPagebinding.profileImg)
            getPurchase()
            getReview()
        }
            //popularityList() -> 얘떄문에 화면 다시 그려질때마다 리스트가 추가 됐던거임 ㅠ resume 화면 갱신용으로 함부로 쓰지 않기...ㅁ7ㅁ8
    }

    override fun setupEvents() {
        myPagebinding.subCountLayout.setOnClickListener {
            myIntent = Intent(mContext, PurchaseListActivity::class.java)
            myIntent.putExtra("data", mSubscriptionList)
            startActivity(myIntent)
        }

        myPagebinding.reviewCountLayout.setOnClickListener {
            myIntent = Intent(mContext, UserReviewListActivity::class.java)
            myIntent.putExtra("data", mReivewList)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        popularityList()
        if (ContextUtil.getLoginToken(mContext) == "") {
            getPurchase()
            getReview()
        }
    }

    fun initAdapter() {
        mTitleList.add("신상품순")
        mTitleList.add("인기순")
        myPageAdapter = MyPageRecyclerAdapter(mContext, mTitleList)
        //myPageAdapter.frag = this
        myPagebinding.myPageRecyclerView.adapter = myPageAdapter
        myPagebinding.myPageRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }

    fun memberCheck() {
        if (ContextUtil.getLoginToken(mContext) == "") {
            ContextUtil.clear(mContext)
            myPagebinding.nonMemberLayout.visibility = View.VISIBLE

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
        } else {
            myPagebinding.memberLayout.visibility = View.VISIBLE
            myPagebinding.nickNmaeTxt.text = GlobalData.loginUser!!.nickName
            Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(myPagebinding.profileImg)
            apiList.getUserPoint().enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    var userPoint = 0
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        for (i in br.data.point) {
                            userPoint += i.amount
                        }
                        myPagebinding.pointTxt.text = userPoint.toString() + "원"
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })
        }
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
                    for (i in reviews) {
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
                    for (i in product) {
                        if (mSugProductList.size <= 5) {
                            mSugProductList.add(i)
                        }
                    }
                    mTotalProductList.add(mSugProductList)
                    myPageAdapter.addData(mTotalProductList)
                    myPageAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun refresh(fragment: Fragment, fragmentManager: FragmentManager) {
        var transaction = fragmentManager.beginTransaction()
        transaction.detach(this).attach(this).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                myPagebinding.nickNmaeTxt.text = GlobalData.loginUser!!.nickName
            }
        }
    }

    fun getPurchase() {
        apiList.getPaymentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mSubscriptionList.clear()
                    mSubscriptionList.addAll(br.data.payments)
                    myPagebinding.purchaseCountTxt.text = mSubscriptionList.size.toString()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun getReview() {
        apiList.getUserReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReivewList.clear()
                    mReivewList.addAll(br.data.reviews)
                    myPagebinding.reviewCountTxt.text = mReivewList.size.toString()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
}