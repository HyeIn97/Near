package com.example.near.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LageCategoryData(
    val id : Int,
    val name : String,
    @SerializedName("small_categories")
    val smallCategory : ArrayList<SmallCategoryData>
) : Serializable