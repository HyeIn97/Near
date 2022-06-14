package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewData (
    val id : Int,
    val title : String,
    val content : String,
    val score : Double,
    @SerializedName("product_id")
    val productId : Int,
    @SerializedName("thumbnail_img")
    val img : String,
    @SerializedName("created_at")
    val date : String,
    val user : UserData,
    val product : ProductData
    ) : Serializable