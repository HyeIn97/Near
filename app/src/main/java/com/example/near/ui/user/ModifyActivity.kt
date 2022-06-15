package com.example.near.ui.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.near.R
import com.example.near.databinding.ActivityModifyBinding
import com.example.near.fragments.MyPageFragment
import com.example.near.models.BasicResponse
import com.example.near.ui.BaseActivity
import com.example.near.ui.MainActivity
import com.example.near.utils.GlobalData
import com.example.near.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.security.Permission

class ModifyActivity : BaseActivity() {
    lateinit var binding : ActivityModifyBinding
    val USERNICK = "nickname"
    val USERPHONE = "phone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify)
        setUpEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        backBtn.setOnClickListener {
            finish()
        }
        binding.modifyBtn.setOnClickListener {
            finish()
        }
        binding.nickModifyBtn.setOnClickListener {
            modifyUserNick()
        }
        binding.phoneModifyBtn.setOnClickListener {
            modifyUserPhone()
        }
        binding.modifyProfileBtn.setOnClickListener {
            //권한 확일 부분 필요
        }
    }

    override fun setValues() {
        titleTxt.text = "회원정보 수정"
        titleTxt.visibility = View.VISIBLE
        backBtn.visibility = View.VISIBLE

        Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(binding.userImg)
        binding.userEmail.text = GlobalData.loginUser!!.email
        binding.userNickEdt.hint = GlobalData.loginUser!!.nickName
        binding.userPhoneEdt.hint = GlobalData.loginUser!!.phone
    }

    fun modifyUserNick(){
        val userNick = binding.userNickEdt.text.toString()
        apiList.patchUserInfo(USERNICK, userNick, null).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                }else{
                    val errorBodyStr = response.errorBody()!!.string()
                    Log.d("errorBodyStr", errorBodyStr)
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }

    fun modifyUserPhone(){
        val userPhone = binding.userPhoneEdt.text.toString()
        apiList.patchUserInfo(USERPHONE, userPhone, null).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()
                }else{
                    val errBody = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errBody)
                    val message =  jsonObj.getString("message")
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            }
        })
    }
    /*fun refresh(){
        val myIntent = Intent(mContext, MainActivity::class.java)
        myIntent.putExtra("")
    }*/

    fun data(){
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
//            어떤 사진을 골랏는지? 파악해보자
//            임시 : 고른 사진을 profileImg에 바로 적용만 (서버전송 X)

//            data? => 이전 화면이 넘겨준 intent
//            data?.data => 선택한 사진이 들어있는 경로 정보 (Uri)
                val dataUri = it.data?.data

//            Uri -> 이미지뷰의 사진 (GLide)
//            Glide.with(mContext).load(dataUri).into(binding.profileImg)

//            API 서버에 사진을 전송 => PUT 메쏘드 + ("/user/image")
//            파일을 같이 첨부해야 => Multipart 형식의 데이터 첨부 활용 (기존 FromData와는 다르다!!)

//            Uri -> File 형태로 변환 -> 그 파일의 실제 경로를 얻어낼 필요가 있다.
                val file = File(URIPathHelper().getPath(mContext, dataUri!!))

//            파일을 retrofit에 첨부할 수 있는 => RequestBody => MultipartBody 형태로 변환
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
                val body = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

                apiList.putUserImg(body).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
//                        1. 선택한 이미지로 UI 프사 변경
                            GlobalData.loginUser = response.body()!!.data.user

                            Glide.with(mContext).load(GlobalData.loginUser!!.profileImg).into(binding.userImg)

//                        2. 토스트로 성공 메세지
                            Toast.makeText(mContext, "프로필 사진이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
    }


}