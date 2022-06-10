package com.example.near.api

import com.example.near.models.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIList {
    //회원정보 조회
    @GET("/user")
    fun getUserInfo() : Call<BasicResponse>
    //회원가입
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSign(@Field("email")email:String, @Field("password")pw: String, @Field("nick_name")nickName:String, @Field("phone")phone:String) : Call<BasicResponse>
    //중복 검사
    @GET("/user/check")
    fun getRequestCheck(@Query("type")type:String, @Query("value")value:String) : Call<BasicResponse>
    //로그인
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(@Field("email")email : String, @Field("password")pw : String) : Call<BasicResponse>
    //아이디 찾기
    @GET("/user/find/email")
    fun getRequestFindId(@Query("nick_name")nickName : String, @Query("phone")phone : String) : Call<BasicResponse>
    //비밀번호 찾기
    @FormUrlEncoded
    @POST("/user/find/password")
    fun postRequestFindPw(@Field("email")email:String, @Field("nick_name")nickName : String) : Call<BasicResponse>
    //마일리지 조회
    @GET("/user/point")
    fun getUserPoint() : Call<BasicResponse>
    //회원 정보 수정
    @FormUrlEncoded
    @PATCH("/user")
    fun patchUserInfo(@Field("field")fieId:String, @Field("value")value:String) : Call<BasicResponse>
    //회원 프로필 이미지 등록
    @FormUrlEncoded
    @PUT("/user/image")
    fun putUserImg(@Field("profile_image")profileImg:String) : Call<BasicResponse>
    //회원 프로필 이미지 삭제
    @DELETE("/user/image")
    fun deleteUserImg() : Call<BasicResponse>
    //회원 카드 목록 조회
    @GET("/user/card")
    fun getUserCardList() : Call<BasicResponse>
    //회원 카드 등록
    @FormUrlEncoded
    @POST("/user/card")
    fun postUserCardAdd(@Field("card_num")cardNum : String, @Field("card_nickname")cardNick:String, @Field("mm_yy")cardPeriod: String, @Field("birthday")birth:String, @Field("password_2digit")pw:String) : Call<BasicResponse>

    //홈 배너 이미지
    @GET("/main/banner")
    fun getBannerImg() : Call<BasicResponse>
    //홈 추천상품
    @GET("/review/ranking")
    fun getReviewRanking() : Call<BasicResponse>
    //홈 모든상품
    @GET("/product")
    fun getAllProductList() : Call<BasicResponse>

}