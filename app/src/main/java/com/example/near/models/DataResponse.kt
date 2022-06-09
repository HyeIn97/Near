package com.example.near.models

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class DataResponse(
    val user : UserData,
    val token : String,
    val products : JsonArray,
    //val products : ArrayList<ProductData>,
    val banners : JsonArray,
    val reviews: JsonArray,
    @SerializedName("point_logs")
    val point : JsonArray
) {
}