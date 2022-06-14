package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartData(
    val id : Int,
    @SerializedName("product_id")
    val productId : Int,
    val product : ProductData,
    val store : StoreData,
    var isChecked : Boolean
) : Serializable