package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SmallCategoryData(
    val id : Int,
    val name : String,
    @SerializedName("large_category_id")
    val largeCategoryId : Int
) : Serializable