package com.example.near.models

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class DataResponse(
    val user : UserData,
    val token : String,
    val product : ProductData,
    val products : ArrayList<ProductData>,
    val banners : JsonArray,
    val reviews: ArrayList<ReviewData>,
    @SerializedName("point_logs")
    val point : ArrayList<PointData>,
    val cards : ArrayList<CardData>,
    val categories : ArrayList<LageCategoryData>,
    val carts : ArrayList<CartData>,
    val replies: ArrayList<RepliesData>,
    val payment : PaymentData,
    val payments : ArrayList<PaymentData>
) {
}