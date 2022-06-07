package com.example.near.models

import com.google.gson.JsonArray

data class DataResponse(
    val user : UserData,
    val token : String,
    val products : JsonArray,
    //val product : ArrayList<ProductData>,
    val banners : JsonArray,
    val reviews : JsonArray
    //val reviews : ArrayList<ReviewData>
) {
}