package com.example.near.models

import com.google.gson.JsonArray

class DataResponse(
    val user : UserData,
    val token : String,
    val product : ArrayList<ProductData>,
    val banners : JsonArray,
    val reviews : JsonArray
) {
}