package com.example.near.api

import com.example.near.models.BasicResponse
import okhttp3.MultipartBody
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
    fun patchUserInfo(@Field("field")fieId:String, @Field("value")value:String, @Field("current_password")currentPw : String?) : Call<BasicResponse>
    //회원 프로필 이미지 등록
    @Multipart
    @PUT("/user/image")
    fun putUserImg(@Part profileImg : MultipartBody.Part) : Call<BasicResponse>
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
    //구독상품 조회
    @GET("/user/payment")
    fun getPaymentList() : Call<BasicResponse>
    //리뷰목록 조회
    @GET("/user/review")
    fun getUserReviewList() : Call<BasicResponse>
    //탈퇴
    @DELETE("/user")
    fun deleteUser(@Query("text") text : String) : Call<BasicResponse>

    //홈 배너 이미지
    @GET("/main/banner")
    fun getBannerImg() : Call<BasicResponse>
    //홈 추천상품
    @GET("/review/ranking")
    fun getReviewRanking() : Call<BasicResponse>
    //홈 모든상품
    @GET("/product")
    fun getAllProductList() : Call<BasicResponse>

    //모든 카테고리 조회
    @GET("/category")
    fun getAllCategory() : Call<BasicResponse>
    //소분류 카테고리 조회
    @GET("/category/small/{small_category_id}")
    fun getSmallCategoryList(@Path("small_category_id")smallID : Int) : Call<BasicResponse>
    //특정 상품 상세보기
    @GET("/product/{product_id}")
    fun getProductDetail(@Path("product_id")id : String) : Call<BasicResponse>

    //장바구니 상품 등록
    @FormUrlEncoded
    @POST("/cart")
    fun postAddCart(@Field("product_id") id : String) : Call<BasicResponse>
    //장바구니 조회
    @GET("/cart")
    fun getCartList() : Call<BasicResponse>
    //장바구니 삭제
    @DELETE("/cart")
    fun deleteCart(@Query("product_id") id : String) : Call<BasicResponse>

    //결제
    @FormUrlEncoded
    @POST("/purchase")
    fun postPurchaseProduct(@Field("product_id") productId : String, @Field("card_id") cardId : String) : Call<BasicResponse>

    //상품별 리뷰조회
    @GET("/review/{review_id}/reply")
    fun getReviewReply(@Path("review_id") id : String) : Call<BasicResponse>
    //리뷰 댓글 작성
    @FormUrlEncoded
    @POST("/review/{review_id}/reply")
    fun postReviewReply(@Path("review_id")id : String, @Field("content")content: String) : Call<BasicResponse>
    //리뷰등록
    @FormUrlEncoded
    @POST("/review")
    fun postReviewSave(@Field("product_id")id : String, @Field("title") title: String, @Field("content")content : String, @Field("score")score : Float, @Field("tag_list")tag: String, @Field("thumbnail_img")thumbnailImg : String?, @Field("review_images") reviewImg : String?) : Call<BasicResponse>
    //모든리뷰 조회(최근순)
    @GET("/review")
    fun getAllReview() : Call<BasicResponse>
}