package com.example.near.models

import com.google.gson.annotations.SerializedName

data class ProductData (
    val id : Int,
    val name : String,
    val price : Int,
    @SerializedName("image_url")
    val img : String
        ) {


}